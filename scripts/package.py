import plyj.parser as plyj
import pretty_printer
import sys


def week_1():
    parser = plyj.Parser()
    percolation = parser.parse_file(file('src/io/akst/algo/week1/Percolation.java'))
    perc_stats = parser.parse_file(file('src/io/akst/algo/week1/PercolationStats.java'))
    union_find = parser.parse_file(file('src/io/akst/algo/week1/IndexUF.java'))

    remove_package_declaration(percolation)
    remove_package_declaration(perc_stats)
    add_class_as_inner_class(union_find, percolation)

    printer = pretty_printer.PrettyPretter(2)
    print printer.print_tree(perc_stats)


def remove_package_declaration(compilation_unit):
    compilation_unit.package_declaration = None


def add_class_as_inner_class(into, outo):
    inner_class   = into.type_declarations[0]
    inner_imports = into.import_declarations

    for ii in inner_imports:
        if all(ii.name.value != oi.name.value for oi in outo.import_declarations):
            outo.import_declarations.append(ii)

    outo.type_declarations[0].body.append(inner_class)


week_callbacks = {
    1: week_1
}


if __name__ == '__main__':
    #
    # trigger valid callback in week_callbacks
    #
    week_callbacks[int(sys.argv[1])]()

