import os
import glob
import csv

extension = 'csv'
result = [i for i in glob.glob('data/test_1.00_5.00_0.10*.{}'.format(extension))]

gens = 0
best = 999999999999
avg = 0
best_file = 0

def row_count(filename):
    with open(filename) as in_file:
        return sum(1 for _ in in_file)

for i, file in enumerate(result):
        file_avg = 0
        last_line_number = row_count(file)
        with open(file) as csvfile:
                reader = csv.reader(csvfile)
                for row in reader:
                        gens += 1
                        if best > float(row[1]):
                                best = float(row[1])
                        avg += float(row[2])
                        file_avg += float(row[2])
        file_avg = file_avg / last_line_number
        print(str(file_avg) + " " + file)

print(gens)
print(best)
print(avg/gens)