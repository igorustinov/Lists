package com.company;

import java.io.OutputStream;
import java.io.PrintStream;
import java.time.Duration;
import java.time.Instant;

/**
 * Created by igoru on 02-Jul-17.
 */
public class CommandHandler {

    private boolean showSize = false;
    private boolean showList = false;

    // -a 20 -b 30 -hashset 1
    // -v verbose output (everything)
    // -s output size
    // -l output full list
    // -h, -help usage
    public void handle(String[] args, PrintStream printStream) {

        try {
            ListIntersectionBuilder bldr = new ListIntersectionBuilder();
            for (int i = 0; i < args.length; i++) {
                if ("-h".equalsIgnoreCase(args[i]) || "-help".equalsIgnoreCase(args[i])) {
                    showUsage(printStream);
                    return;
                }
                if ("-a".equalsIgnoreCase(args[i])) {
                    bldr = bldr.sizeOne(Integer.parseInt(args[++i]));
                } else if ("-b".equalsIgnoreCase(args[i])) {
                    bldr = bldr.sizeTwo(Integer.parseInt(args[++i]));
                } else if ("-hashset".equalsIgnoreCase(args[i])) {
                    final int val = Integer.parseInt(args[++i]);
                    ListIntersectionBuilder.ListToUseForHashSet valEnum = ListIntersectionBuilder.ListToUseForHashSet.DEFAULT;
                    if (val == 1) {
                        valEnum = ListIntersectionBuilder.ListToUseForHashSet.FIRST;
                    } else if (val == 2) {
                        valEnum = ListIntersectionBuilder.ListToUseForHashSet.SECOND;
                    }
                    bldr = bldr.listToUseForHashset(valEnum);
                } else if (args[i].startsWith("-")) {
                    char[] charArray = args[i].toCharArray();
                    for (int j = 1; j < charArray.length; j++) {
                        char c = charArray[j];
                        switch (c) {
                            case 'v':
                                showSize = true;
                                showList = true;
                                break;
                            case 'l':
                                showList = true;
                                break;
                            case 's':
                                showSize = true;
                                break;
                            default:
                        }
                    }
                }
            }
            final ListIntersection<Long> intersection = bldr.build();
            Instant start = Instant.now();
            int counter = 0;
            for (Long aLong : intersection) {
                if (showList) {
                    printStream.print(aLong + " ");
                }
                if (showSize) {
                    counter++;
                }
            }
            printStream.println();

            if (showSize) {
                printStream.println("Size: " + counter);
            }

            Instant finish = Instant.now();
            final Duration between = Duration.between(start, finish);
            printStream.println("Completed: " + between);
        } catch (IllegalArgumentException e) {
            printStream.println("Error parsing command. use with    -h or -help to see usage");
        }
    }

    private void showUsage(PrintStream ps) {
        ps.println("List Intersection utility usage:");
        ps.println("-a sets the size of list A");
        ps.println("-b sets the size of list B");
        ps.println("-hashset 1 sets the first list to be used as hashset");
        ps.println("-v verbose output (everything)");
        ps.println("-s outputs the size of the resulting list");
        ps.println("-l outputs full resulting list");
        ps.println("-h, -help prints this help");
        ps.println("Example (generates two lists with size 20 and 300, puts the second one for in HashSet, prints the size and the resulting list." +
                "-a 20 -b 300 -hashset");
    }

}
