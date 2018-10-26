import os
import glob
import csv

extension = 'csv'
result = [i for i in glob.glob('data/test_1.00_5.00_0.50*.{}'.format(extension))]

gens = 0
best = 999999999999
avg = 0
best_file = 999999999999
best_file_name = "test"

def row_count(filename):
    with open(filename) as in_file:
        return sum(1 for _ in in_file)

for i, file in enumerate(result):
        file_avg = 0
        last_line_number = row_count(file)
        with open(file) as csvfile:
                reader = csv.DictReader(csvfile)
                for row in reader:
                        gens += 1
                        if best > float(row["best"]):
                                best = float(row["best"])
                        avg += float(row["avg"])
                        file_avg += float(row["avg"])
        file_avg = file_avg / last_line_number
        if best_file > file_avg:
                best_file_name = file
                best_file = file_avg

print(gens)
print(best)
print(avg/gens)
print(best_file_name)