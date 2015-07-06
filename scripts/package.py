import plyj.parser as plyj

import pretty_printer
import zipfile
import json
import sys


def package_job(files, location):
    parser = plyj.Parser()
    printer = pretty_printer.PrettyPretter(2)
    with zipfile.ZipFile(location, mode='w') as zip_file:
        for f in files:
            parsed = parser.parse_file(file(f['r_path']))
            remove_package_declaration(parsed)
            remove_princeton_imports(parsed)
            zip_file.writestr(f['w_path'], printer.print_tree(parsed))


def remove_package_declaration(compilation_unit):
    compilation_unit.package_declaration = None


def remove_princeton_imports(tree):
    princeton_prefix = 'edu.princeton.cs'
    new_prefixes = []
    for dec in tree.import_declarations:
        if not dec.name.value.startswith(princeton_prefix):
            new_prefixes.append(dec)
    tree.import_declarations = new_prefixes


def add_class_as_inner_class(into, outo):
    inner_class   = into.type_declarations[0]
    inner_imports = into.import_declarations

    for ii in inner_imports:
        if all(ii.name.value != oi.name.value for oi in outo.import_declarations):
            outo.import_declarations.append(ii)

    inner_class.modifiers.insert(1, 'static')
    if 'public' in inner_class.modifiers:
        inner_class.modifiers[inner_class.modifiers.index('public')] = 'private'
    elif 'private' not in inner_class.modifiers:
        inner_class.modifiers.insert(0, 'private')

    outo.type_declarations[0].body.append(inner_class)


if __name__ == '__main__':
    with open('config/zip_targets.json', 'r') as f:
        weeks = json.load(f)['weeks']
        package_job(weeks[sys.argv[1]], sys.argv[2])

