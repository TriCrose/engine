#!/usr/bin/perl
use strict;
use warnings;

# usage: concat.pl <directory> <output-file>
# merges all the files in a given directory into one file with the following format:
# 1st 2 bytes - number of files
# for each file:
#   1st 4 bytes - size in bytes
#   remaining bytes - file data
# files are merged in alphabetical order

my ($dir, $out);

if (@ARGV == 0) {
    print "Files directory: ";
    $dir = <STDIN>; chomp $dir;
    print "Output file: ";
    $out = <STDIN>; chomp $out;
} elsif (@ARGV == 1) {
    print "Files directory: ";
    $dir = <STDIN>; chomp $dir;
    $out = $ARGV[0];
} else {
    $dir = $ARGV[0];
    $out = $ARGV[1];
}

# open directory for reading and then remove "." and ".."
opendir DIR, $dir or die "Cannot open directory $dir: $!";
my @files = sort { lc($a) cmp lc($b) } readdir DIR;
splice(@files, 0, 2);

# open the output file for writing and write the file count
open OUT, ">", $out;
binmode OUT;
select OUT;
print pack("S", scalar @files);

for (@files) {
    # open file as binary for reading
    open FILE, "$dir/$_" or die "Cannot open file $dir/$_: $!";
    binmode FILE;
    my $contents;
    read(FILE, $contents, -s "$dir/$_", 0);

    # write the file size and the contents
    print pack("L", length $contents), $contents;
    close FILE;
}

# close handles
close OUT;
closedir DIR;