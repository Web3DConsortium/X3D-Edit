package org.web3d.x3d.config.util;

import java.io.*;

import java.nio.file.*;
import java.nio.file.attribute.*;

import static java.nio.file.FileVisitResult.*;

/**
 * Sample code that finds files that match the specified glob pattern.
 * 
 * Borrowed from: https://docs.oracle.com/javase/tutorial/essential/io/find.html
 * 
 * For more information on what constitutes a glob pattern, see
 * https://docs.oracle.com/javase/tutorial/essential/io/fileOps.html#glob
 *
 * The file or directories that match the pattern are printed to
 * standard out and matched file Path returned to the caller.
 *
 * When executing this application, you must put the glob pattern
 * in quotes, so the shell will not expand any wild cards:
 *              java Find . -name "*.java"
 * 
 * @author <a href="mailto:tdnorbra@nps.edu">Terry D. Norbraten</a>
 */
public class FindPath extends SimpleFileVisitor<Path> {

    private final PathMatcher matcher;
    private Path file;

    public FindPath(String pattern) {
        super();
        matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
    }

    /** Compares the glob pattern against the file or directory name.
     * 
     * @param file the Path to compare against the glob pattern
     */
    private void find(Path file) {
        Path name = file.getFileName();
        if (name != null && matcher.matches(name)) {
            System.out.println("Success! found: " + file);
            this.file = file;
        }
    }

    /** Prints the total number of matches to standard out and return the
     * Path match.
     * 
     * @return the Path of the discovered file
     */
    public Path get() {
        return file;
    }

    // Invoke the pattern matching
    // method on each file.
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        find(file);
        return CONTINUE;
    }

    // Invoke the pattern matching
    // method on each directory.
    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        find(dir);
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        System.err.println(exc);
        return CONTINUE;
    }

    static void usage() {
        System.err.println("java Find <path>" + " -name \"<glob_pattern>\"");
        System.exit(-1);
    }

    public static void main(String[] args) throws IOException {

        if (args.length < 3 || !args[1].equals("-name")) {
            usage();
        }

        Path startingDir = Paths.get(args[0]);
        String pattern = args[2];

        FindPath finder = new FindPath(pattern);
        Files.walkFileTree(startingDir, finder);
        finder.get();
    }
}
