#!/usr/bin/perl
use strict;
use warnings;

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

opendir DIR, $dir or die "Cannot open directory $dir: $!";
my @files = readdir DIR;
splice @files, 0, 2;

open OUT, ">", $out;
binmode OUT;
select OUT;
print scalar @files;

for (@files) {
    open FILE, "$dir/$_" or die "Cannot open file $dir/$_: $!";
    binmode FILE;
    my $contents;
    read(FILE, $contents, -s "$dir/$_", 0);
    print length $contents, $contents;
    close FILE;
}

closedir(DIR);