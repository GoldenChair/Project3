import java.io.*;  // Import the File class
import java.io.IOException;  // Import the IOException class to handle errors
import java.util.*;

public class main {

    public static void main(String[] args) {

        int numPeople;
        int counter = 0;
        String placeHolder, person;
        Set<String> people;
        Map<String, Set<String>> placeWithNames = new HashMap<>();
        try {
            File f = new File("test.txt");
            Scanner file = new Scanner(f);
            numPeople = Integer.parseInt(file.nextLine());
            file.nextLine();
            while(counter != numPeople){
                counter++;
                person = file.nextLine();
                placeHolder = file.nextLine();
                while(!placeHolder.equals("")) {
                    if(!placeWithNames.containsKey(placeHolder)) {
                        people = new HashSet<>();
                        placeWithNames.put(placeHolder, people);
                    }
                    placeWithNames.get(placeHolder).add(person);
                    placeHolder = file.nextLine();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        placeWithNames.entrySet().forEach(entry->{
            System.out.println(entry.getKey() + " " + entry.getValue());
        });
        System.out.println(placeWithNames.size());
        AdjMatGraph test = new AdjMatGraph(placeWithNames.size());

        Map<String, Integer> index = new HashMap<>();
        ArrayList<String> namePlaces = new ArrayList<String>();
        int counter2 = 0;
        for(Map.Entry<String, Set<String>> entry : placeWithNames.entrySet()){
            System.out.println(entry.getKey() + " " + counter2);
            index.put(entry.getKey(), counter2);
            namePlaces.add(entry.getKey());
            counter2++;

        }

        for(Map.Entry<String, Set<String>> entry1 : placeWithNames.entrySet()){
            for(Map.Entry<String, Set<String>> entry2 : placeWithNames.entrySet()){
                for(String s : entry2.getValue()) {
                    if (entry1.getValue().contains(s)) {
                        test.addEdge(index.get(entry1.getKey()),index.get(entry2.getKey()));
                    }
                }
            }
        }

        test.print2D();

        int[] key = test.dijkstra(0);
        System.out.println("=========================");
        int max = findMax(key);
        for (int i = 0; i < index.size() ; i++) {
            System.out.println("Place of origin: " + 'A' + " level of infection removed  " +  namePlaces.get(i) +
                    " distance: " + key[i]);
        }
        for(int i : key){
            System.out.print(i + " ");
        }
        System.out.println();
        Set<String> names = new HashSet<>();
        int counter3;
        System.out.println("People are listed with their level of infection from starting point." + "\nIf their level removed is 2147483647 then they are not infected at all");
        for(int i = 0; i <= max; i++){
            counter3 = 0;
            for(int j : key){
                if(j == i){
                    for(String s : placeWithNames.get(namePlaces.get(counter3))){
                        if (!names.contains(s)){
                            System.out.println(s + " Is level " + (i+1) + " infected");
                            names.add(s);
                        }
                    }
                }
                counter3++;
            }
        }

    }


    public static Integer findMax(int[] list)
    {
        int max = 0;
        for(int i : list){
            if(i > max){
                max = i;
            }
        }
        return max;
    }
}



