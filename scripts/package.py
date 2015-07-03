import plyj.parser as plyj

import pretty_printer
import zipfile
import sys


def percolation(location):
    parser = plyj.Parser()
    percolation = parser.parse_file(file('src/io/akst/algo/week1/Percolation.java'))
    perc_stats = parser.parse_file(file('src/io/akst/algo/week1/PercolationStats.java'))
    union_find = parser.parse_file(file('src/io/akst/algo/week1/IndexUF.java'))

    #
    # currently there is an issue with adding the appropiate dimentions
    #
    union_find.type_declarations[0]\
        .body[0]\
        .type\
        .dimensions = 1

    remove_package_declaration(percolation)
    remove_package_declaration(perc_stats)
    add_class_as_inner_class(union_find, percolation)

    printer = pretty_printer.PrettyPretter(2)
    with zipfile.ZipFile(location, mode='w') as percolation_zip:
        percolation_zip.writestr('Percolation.java', printer.print_tree(percolation))
        percolation_zip.writestr('PercolationStats.java', printer.print_tree(perc_stats))


def remove_package_declaration(compilation_unit):
    compilation_unit.package_declaration = None


def add_class_as_inner_class(into, outo):
    inner_class   = into.type_declarations[0]
    inner_imports = into.import_declarations

    for ii in inner_imports:
        if all(ii.name.value != oi.name.value for oi in outo.import_declarations):
            outo.import_declarations.append(ii)

    inner_class.modifiers.insert(1, 'static')
    outo.type_declarations[0].body.append(inner_class)


week_callbacks = {
    1: percolation
}


if __name__ == '__main__':
    #
    # trigger valid callback in week_callbacks
    #
    week_callbacks[int(sys.argv[1])](sys.argv[2])

