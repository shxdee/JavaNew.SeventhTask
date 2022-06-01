package com.shxdee.seventhtask;

import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertTrue;

public class StreamProcessorTest {

    @Test
    public void intStreamTest(){
        try(OutputStream out = new FileOutputStream("intStream.txt");
            InputStream in = new FileInputStream("intStream.txt")){
            int[] arr = {1, 2, 3, 4};
            int[] arr2 = new int[arr.length];
            StreamProcessor.writeArrayBite(out, arr);
            StreamProcessor.readArrayBite(in, arr2);
            assertEquals(arr, arr2);
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Test
    public void charStreamTest(){
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        try (Writer writer = new OutputStreamWriter(
                new FileOutputStream("charStream.txt"),
                StandardCharsets.UTF_8)) {
            StreamProcessor.writeArraySym(writer, arr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int[] ints = new int[arr.length];
        try (InputStreamReader reader = new InputStreamReader(
                new FileInputStream("charStream.txt"),
                StandardCharsets.UTF_8)){
            StreamProcessor.readArraySym(reader, ints);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(arr, ints);
    }

    @Test
    public void readFromPositionTest() throws IOException {
        RandomAccessFile stream = new RandomAccessFile("readFromPosition1.txt", "rw");
        int pos = 1 * Integer.BYTES;
        stream.writeInt(7);
        stream.writeInt(8);
        stream.writeInt(9);
        int[] result = StreamProcessor.readFromPosition(stream, pos);
        int[] exp = {8, 9};
        assertEquals(result, exp);
        stream.close();
    }

    @Test
    public void getFilesByExtensionTest() {
        File mockFolder = mock(File.class);
        File mockDir = mock(File.class);
        when(mockDir.isDirectory()).thenReturn(true);
        when(mockDir.getName()).thenReturn("dir.java");
        File[] mockList = {};
        when(mockDir.listFiles()).thenReturn(mockList);
        File[] mockListFiles = {
                mockDir,
                new File("test.java"),
                new File("test2"),
                new File("test3.java")
        };
        when(mockFolder.listFiles()).thenReturn(mockListFiles);
        List<File> files = StreamProcessor.getFilesByExtension(mockFolder, ".java");
        assertEquals(3, files.size());
        assertEquals(files.get(0).getName(), "dir.java");
        assertEquals(files.get(1).getName(), "test.java");
        assertEquals(files.get(2).getName(), "test3.java");
    }

    @Test
    public void getFilesByPatternTest() {
        File mockFolder = mock(File.class);
        File mockDir = mock(File.class);
        when(mockDir.isDirectory()).thenReturn(true);
        when(mockDir.getName()).thenReturn("dir.java");
        File[] mockList = {};
        when(mockDir.listFiles()).thenReturn(mockList);
        File[] mockListFiles = {
                mockDir,
                new File("test.java"),
                new File("test2"),
                new File("test3.java")
        };
        when(mockFolder.listFiles()).thenReturn(mockListFiles);
        List<File> files = null;
        files = StreamProcessor.getFilesByPattern(mockFolder, Pattern.compile(".+\\.java"));
        assertEquals(3, files.size());
        assertEquals(files.get(0).getName(), "dir.java");
        assertEquals(files.get(1).getName(), "test.java");
        assertEquals(files.get(2).getName(), "test3.java");
    }

    @Test
    public static void SerializeAndDeserializeTest() {
        Person person = new Person("Проскурин", "Евгений", "Юрьевич", LocalDate.of(2002, 7, 26));
        Person person1 = new Person("Арбузов", "Дмитрий", "Олегович", LocalDate.of(2004, 3, 14));
        Person person2 = new Person("Телегин", "Матвей", "Александрович", LocalDate.of(2004, 9, 23));
        Person person3 = new Person("Проскурин", "Антон", "Владимирович", LocalDate.of(2012, 5, 16));
        Person person4 = new Person("Васильев", "Василий", "Васильевич", LocalDate.of(2000, 11, 7));
        Flat flat1 = new Flat(61, 90.5, new ArrayList<Person>(Collections.singletonList(person1)));
        Flat flat2 = new Flat(10, 40.99, new ArrayList<Person>(Arrays.asList(person2, person3, person4)));
        List<Flat> flats = new ArrayList<>(Arrays.asList(flat1, flat2));
        House house = new House("12345", "abcde", person, flats);
        try(FileOutputStream fileOutputStream = new FileOutputStream("file.house")) {
            HouseService.serialize(fileOutputStream, house);
        } catch (IOException e) {
            e.printStackTrace();
        }

        House result = null;
        try(FileInputStream fileInputStream = new FileInputStream("file.house")) {
            result = HouseService.deserialize(fileInputStream);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        assertEquals(house, result);
        HouseService.csv(result);

        result = null;
        try {
            HouseService.serializationJackson("jacksonFile.house", house);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            result = HouseService.deserializationJackson("jacksonFile.house");
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(house, result);

        String str1 = "{\"cadNumber\":\"12345\",\"address\":\"abcde\",\"seniorHousework\":\"1222\"}";
        String str2 = "{\"cadNumber\":\"12345\",\"seniorHousework\":\"1222\",\"address\":\"abcde\"}";

        try {
        assertTrue(HouseService.isEqualsJson(str1, str2));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}