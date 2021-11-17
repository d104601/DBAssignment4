import java.io.*;
import java.util.*;

class action
{
    static void setup(Scanner infile1, Scanner infile2, ArrayList<List<String>> file1, ArrayList<List<String>> file2)
    {
        String[] temp;
        List<String> templist;
        while(infile1.hasNext())
        {
            templist = new ArrayList<>();
            temp = infile1.nextLine().split(",");
            Collections.addAll(templist, temp);
            file1.add(templist);
        }

        while(infile2.hasNext())
        {
            templist = new ArrayList<>();

            temp = infile2.nextLine().split(",");
            Collections.addAll(templist, temp);

            file2.add(templist);
        }
    }

    static void just(List<List<String>> file1, List<List<String>> file2, List<List<String>> result)
    {
        List<String> temp;
        int max = Math.min(file1.size(), file2.size());

        for(int i = 0; i < max; i++)
        {
            temp = new ArrayList<>();
            for(int j = 0; j < file1.get(i).size(); j++)
            {
                temp.add(file1.get(i).get(j));
            }
            for(int j = 0; j < file2.get(i).size(); j++)
            {
                temp.add(file2.get(i).get(j));
            }

            result.add(temp);
        }
    }

    static void merge(List<List<String>> file1, List<List<String>> file2, List<List<String>> result)
    {
        int key1 = 0;
        int key2 = 0;

        String keyValue1;
        String keyValue2;
        int mark = 0;

        List<String> temp;


        for(int i = 0; i < file1.size(); i++)
        {
            keyValue1 = file1.get(i).get(key1);
            for(int j = mark; j < file2.size(); j++)
            {
                keyValue2 = file2.get(j).get(key2);

                if(keyValue1.equals(keyValue2))
                {
                    temp = new ArrayList<>();
                    for(int k = 0; k < file1.get(i).size(); k++)
                    {
                        temp.add(file1.get(i).get(k));
                    }
                    for(int k = 0; k < file2.get(j).size(); k++)
                    {
                        if(k != key2)
                            temp.add(file2.get(j).get(k));
                    }
                    result.add(temp);
                    break;
                }
                else
                {
                    if(keyValue1.compareTo(keyValue2)>0)
                        mark++;
                }
            }
        }
    }

    static void loop(List<List<String>> file1, List<List<String>> file2, List<List<String>> result)
    {
        int key1 = 0;
        int key2 = 0;

        List<String> temp;

        System.out.println(key1);
        System.out.println(key2);
        //compare key and merge it
        for (List<String> strings1 : file1)
        {
            for (List<String> strings : file2) {
                if (strings1.get(key1).equals(strings.get(key2))) {
                    temp = new ArrayList<>();
                    for (String s : strings1) {
                        temp.add(s);
                    }
                    for (int k = 0; k < strings.size(); k++) {
                        if (k != key2)
                            temp.add(strings.get(k));
                    }
                    result.add(temp);
                }
            }
        }
    }

    static void hash(List<List<String>> file1, List<List<String>> file2, List<List<String>> result)
    {
        int key1 = 0;
        int key2 = 0;

        List<String> temp;

        HashMap<String, List<String>> hash = new HashMap<>();

        for(int i = 0; i < file2.size(); i++)
        {
            temp = new ArrayList<>();
            for(int j = 0; j < file2.get(i).size(); j++)
            {
                if(j != key2)
                    temp.add(file2.get(i).get(j));
            }
            hash.put(file2.get(i).get(key2), temp);
        }

        for(int i = 0; i < file1.size(); i++)
        {
            temp = new ArrayList<>();
            for(int j = 0; j < file1.get(i).size(); j++)
            {
                temp.add(file1.get(i).get(j));
            }

            temp.addAll(hash.get(file1.get(i).get(key1)));
            result.add(temp);
        }
    }

    static void print(List<List<String>> result)
    {
        for (List<String> strings : result)
        {
            System.out.println();
            for (String string : strings) {
                System.out.print(string + ", ");
            }
        }
    }
}

public class main {
    public static void main(String[] args)
    {
        try
        {
            Scanner inFile1 = new Scanner(new FileReader(args[1]));
            Scanner inFile2 = new Scanner(new FileReader(args[2]));

            ArrayList<List<String>> file1 = new ArrayList<>();
            ArrayList<List<String>> file2 = new ArrayList<>();
            ArrayList<List<String>> result = new ArrayList<>();

            action.setup(inFile1, inFile2, file1, file2);

            switch (args[0])
            {
                case "just" -> {
                    System.out.println("just join");
                    action.just(file1, file2, result);
                    action.print(result);
                }
                case "merge" -> {
                    System.out.println("merge");
                    action.merge(file1, file2, result);
                    action.print(result);
                }
                case "loop" -> {
                    System.out.println("loop");
                    action.loop(file1, file2, result);
                    action.print(result);
                }
                case "hash" -> {
                    System.out.println("hash");
                    action.hash(file1, file2, result);
                    action.print(result);
                }
                default -> System.out.println("Please enter type of join(just, merge, loop, or hash) and then two csv files. " +
                        "Please use sorted file for merge join.");
            }
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            System.out.println("Please enter type of join(just, merge, loop, or hash) and then two .csv files. " +
                    "Please use sorted file for merge join.");
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File doesn't exist or Improper files.");
        }
    }
}