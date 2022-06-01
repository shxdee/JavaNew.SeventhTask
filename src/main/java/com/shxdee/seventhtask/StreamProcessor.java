package com.shxdee.seventhtask;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class StreamProcessor {
    // 1
    public static void writeArrayBite(OutputStream out, int[] arr) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        for (int i: arr) {
            dos.writeInt(i);
        }
    }

    public static void readArrayBite(InputStream in, int[] arr) throws IOException {
        DataInputStream dis = new DataInputStream(in);
        for (int i = 0; i < arr.length; i++) {
            arr[i] = dis.readInt();
        }
    }

    // 2
    public static void writeArraySym(Writer out, int[] arr) throws IOException {
        PrintWriter pw = new PrintWriter(out);
        for (int i: arr) {
            pw.write(String.valueOf(i));
            pw.write(' ');
        }
    }

    public static void readArraySym(Reader in, int[] arr) throws IOException {
        BufferedReader reader = new BufferedReader(in);
        String[] numbers = reader.readLine().split(" ");
            for(int i = 0; i < arr.length; i++){
                arr[i] = Integer.parseInt(numbers[i]);
            }
        }

    // 3
    public static int[] readFromPosition(RandomAccessFile raf, int position) throws IOException {
        int[] res = new int[(int) (raf.length() - position )/ Integer.BYTES];
        raf.seek(position);
        for (int i = 0; i < res.length; i++) {
            res[i] = raf.readInt();
        }
        return res;
    }

    // 4
    public static List<File> getFilesByExtension(File catalog, final String ext) {
        return Arrays.stream(Objects.requireNonNull(catalog.listFiles()))
                .filter(item -> item.getName().endsWith(ext)).collect(Collectors.toList());
    }

    // 5*
    public static void getFilesRecur(File catalog, Pattern pattern, List<File> result) {
        if(catalog != null) {
            for (File f : catalog.listFiles()) {
                if (f.isDirectory()) {
                    getFilesRecur(f, pattern, result);
                }
                if (Pattern.matches(pattern.pattern(), f.getName())) {
                    result.add(f);
                }
            }
        }
    }

    public static List<File> getFilesByPattern(File catalog, Pattern pattern) {
        List<File> result = new ArrayList<>();
        getFilesRecur(catalog, pattern, result);
        return result;
    }
}
