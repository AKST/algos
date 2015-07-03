import plyj.parser as plyj

class UnsupportedASTError(ValueError):
    pass


class PrettyPretter:
    def __init__(self, indentation=4):
        self.out                = None
        self.current_indent     = None
        self.indentation_amount = indentation

    def print_tree(self, tree):
        self.out            = ''
        self.current_indent = 0
        self._print_tree(tree)
        return self.out

    def line_break(self):
        self.out += '\n'
        self.out += (self.indentation_amount * ' ') * self.current_indent

    def block(self, block):
        if isinstance(block, plyj.Block):
            block = block.statements
        if isinstance(block, list):
            self.out += ' {'
            self.current_indent += 1
            for statement in block:
                if type(statement) != plyj.IfThenElse:
                    self.line_break()
                skip_semicolon = self._print_tree(statement)
                if not skip_semicolon:
                    self.out += ';'
            self.current_indent -= 1
            self.line_break()
            self.out += '}'
        else:
            self.out += ' '
            self._print_tree(block)
            self.out += ';'

    def semicolon_seperated(self, values):
        for arg in values:
            self._print_tree(arg)
            if arg != values[-1]:
                self.out += '; '

    def comma_seperate(self, values):
        for arg in values:
            self._print_tree(arg)
            if arg != values[-1]:
                self.out += ', '

    def _print_tree(self, tree, previous=None):
        if isinstance(tree, str):
            self.out += tree
        elif type(tree) == plyj.CompilationUnit:
            if tree.package_declaration != None:
                self._print_tree(tree.package_declaration)
            for import_dec in tree.import_declarations:
                self._print_tree(import_dec)
            for type_dec in tree.type_declarations:
                self._print_tree(type_dec)
        elif type(tree) == plyj.PackageDeclaration:
            self.out += 'package '
            self._print_tree(tree.name)
            if tree.modifiers:
                raise NotImplemented('modifiers PackageDeclaration')
            self.out += ';'
            self.line_break()
            self.line_break()
        elif type(tree) == plyj.ImportDeclaration:
            if tree.on_demand:
                raise NotImplemented('no_demand ImportDeclaration')
            if tree.static:
                self.out += 'static '
            self.out += 'import '
            self._print_tree(tree.name)
            self.out += ';'
            self.line_break()
        elif type(tree) == plyj.ClassDeclaration:
            self.line_break()
            self.line_break()
            for modifier in tree.modifiers:
                self.out += modifier + ' '
            self.out += 'class ' + tree.name
            if tree.type_parameters:
                self.out += '<'
                self.comma_seperate(tree.type_parameters)
                self.out += '>'
            if tree.extends:
                raise UnsupportedASTError('extends ClassDeclaration')
            if tree.implements:
                raise UnsupportedASTError('implements ClassDeclaration')
            self.out += ' {'
            self.current_indent += 1
            self.line_break()
            self.line_break()
            for declaration in tree.body:
                self._print_tree(declaration)
            self.current_indent -= 1
            self.line_break()
            self.out += '}'
        elif type(tree) == plyj.VariableDeclaration:
            for modifier in tree.modifiers:
                self.out += modifier + ' '
            self._print_tree(tree.type)
            self.out += ' '
            for declaration in tree.variable_declarators:
                self._print_tree(declaration)
        elif type(tree) == plyj.FieldDeclaration:
            for modifier in tree.modifiers:
                self.out += modifier + ' '
            self._print_tree(tree.type)
            self.out += ' '
            for declaration in tree.variable_declarators:
                self._print_tree(declaration)
            self.out += ';'
            self.line_break()
        elif type(tree) == plyj.Type:
            self._print_tree(tree.name)
            if tree.enclosed_in:
                raise UnsupportedASTError('enclosed_in Type')
            if tree.type_arguments:
                self.out += '<'
                for type_arg in tree.type_arguments:
                    self._print_tree(type_arg)
                self.out += '>'
            if tree.dimensions:
                self.out += '[]' * tree.dimensions
        elif type(tree) == plyj.VariableDeclarator:
            self._print_tree(tree.variable)
            if tree.initializer:
                self.out += ' = '
                self._print_tree(tree.initializer)
        elif type(tree) == plyj.Literal:
            self.out += tree.value
        elif type(tree) == plyj.Variable:
            self.out += tree.name
        elif type(tree) == plyj.Name:
            self.out += tree.value
        elif type(tree) == plyj.FormalParameter:
            self._print_tree(tree.type)
            self.out += ' '
            if tree.vararg:
                self.out += '... '
            self._print_tree(tree.variable)
        elif type(tree) == plyj.MethodDeclaration:
            self.line_break()
            self.line_break()
            for modifier in tree.modifiers:
                self.out += modifier + ' '
            self._print_tree(tree.return_type)
            self.out +=  ' ' + tree.name + ' '
            if tree.type_parameters:
                raise UnsupportedASTError('type_parameters ConstructorDeclaration')
            self.out += '('
            self.comma_seperate(tree.parameters)
            self.out += ')'
            if tree.throws:
                raise UnsupportedASTError('throws ConstructorDeclaration')
            if tree.abstract:
                self.out += ';'
            else:
                self.block(tree.body)
            if tree.extended_dims:
                raise UnsupportedASTError('extended_dims ConstructorDeclaration')
        elif type(tree) == plyj.ConstructorDeclaration:
            self.line_break()
            for modifier in tree.modifiers:
                self._print_tree(modifier)
                self.out += ' '
            self.out += tree.name + ' '
            self.out += '('
            self.comma_seperate(tree.parameters)
            self.out += ')'
            if tree.throws:
                raise UnsupportedASTError('throws ConstructorDeclaration')
            self.block(tree.block)
        elif type(tree) == plyj.Assignment:
            self._print_tree(tree.lhs)
            self.out += ' ' + tree.operator + ' '
            self._print_tree(tree.rhs)
        elif type(tree) == plyj.FieldAccess:
            self._print_tree(tree.target)
            self.out += '.' + tree.name
        elif type(tree) == plyj.Multiplicative or \
                type(tree) == plyj.Additive or \
                type(tree) == plyj.Equality or \
                type(tree) == plyj.ConditionalOr or \
                type(tree) == plyj.Relational:
            self.out += '('
            self._print_tree(tree.lhs)
            self.out += ' ' + tree.operator + ' '
            self._print_tree(tree.rhs)
            self.out += ')'
        elif type(tree) == plyj.InstanceCreation:
            self.out += 'new '
            self._print_tree(tree.type)
            if tree.type_arguments:
                raise UnsupportedASTError('type_arguments InstanceCreation')
            self.out += '('
            self.comma_seperate(tree.arguments)
            self.out += ')'
        elif type(tree) == plyj.MethodInvocation:
            if tree.target:
                self._print_tree(tree.target)
                self.out += '.'
            self.out += tree.name
            if tree.type_arguments:
                raise UnsupportedASTError('type_arguments MethodInvocation')
            self.out += '('
            self.comma_seperate(tree.arguments)
            self.out += ')'
        elif type(tree) == plyj.IfThenElse:
            if not isinstance(previous, plyj.IfThenElse):
                self.line_break()
            else:
                self.out += ' '
            self.out += 'if ('
            self._print_tree(tree.predicate)
            self.out += ')'
            self.block(tree.if_true)
            if tree.if_false:
                self.line_break()
                self.out += 'else'
                self._print_tree(tree.if_false)
            return True
        elif type(tree) == plyj.TypeParameter:
            self._print_tree(tree.name)
            if tree.extends:
                self.out += ' extends '
                self.comma_seperate(tree.extends)
        elif type(tree) == plyj.Return:
            self.out += 'return'
            if tree.result:
                self.out += ' '
                self._print_tree(tree.result)
        elif type(tree) == plyj.Throw:
            self.out += 'throw '
            self._print_tree(tree.exception)
        elif type(tree) == plyj.Block:
            self.block(tree.statements)
        elif type(tree) == plyj.Cast:
            self.out += '(('
            self._print_tree(tree.target)
            self.out += ') '
            self._print_tree(tree.expression)
            self.out += ')'
        elif type(tree) == plyj.DoWhile:
            self.out += 'do'
            self.block(tree.body)
            self.out += 'while ('
            self._print_tree(tree.predicate)
            self.out += ')'
        elif type(tree) == plyj.While:
            self.out += 'while ('
            self._print_tree(tree.predicate)
            self.out += ')'
            self.block(tree.body)
            return True
        elif type(tree) == plyj.For:
            self.out += 'for ('
            self._print_tree(tree.init)
            self.out += '; '
            self._print_tree(tree.predicate)
            self.out += '; '
            self.semicolon_seperated(tree.update)
            self.out += ') '
            self._print_tree(tree.body)
            return True
        elif type(tree) == plyj.ArrayCreation:
            self.out += 'new '
            self._print_tree(tree.type)
            for dim in tree.dimensions:
                self.out += '['
                self._print_tree(dim)
                self.out += ']'
        elif type(tree) == plyj.ArrayAccess:
            self._print_tree(tree.target)
            self.out += '['
            self._print_tree(tree.index)
            self.out += ']'
        elif type(tree) == plyj.Unary:
            if tree.sign == 'x++':
                self._print_tree(tree.expression)
                self.out += '++'
            elif tree.sign == 'x--':
                self._print_tree(tree.expression)
                self.out += '--'
            elif tree.sign == '!':
                self.out += '!'
                self._print_tree(tree.expression)
            else:
                raise UnsupportedASTError(tree)
        elif type(tree) == plyj.Annotation:
            self.out += '@'
            self._print_tree(tree.name)
            if tree.single_member:
                self.out += '('
                self._print_tree(tree.single_member)
                self.out += ')'
            elif tree.members:
                self.out += '('
                self.comma_seperate(tree.members)
                self.out += ')'
        else:
            print self.out
            raise UnsupportedASTError(tree)
