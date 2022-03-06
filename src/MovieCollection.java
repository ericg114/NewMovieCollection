import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private Scanner scanner;

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter option: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Wont' work");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as option 1 in the results list; better for user!
            int optionNum = i + 1;

            System.out.println("" + optionNum + ". " + title);
        }

        System.out.println("Which one do you want to learn more about?");
        System.out.print("Enter the number- ");

        int option = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(option - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n Press Enter when your done");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast()
    {
        System.out.print("Which cast member would you like to search? ");
        String name = scanner.nextLine();
        ArrayList<String> castMembers = new ArrayList<String>();
        ArrayList<Movie> moviesWithWord = new ArrayList<Movie>();


        for (Movie movie : movies) {
            String castList = movie.getCast();
            if (castList.contains(name)) {
                moviesWithWord.add(movie);
                System.out.println(castList);
                String[] actorList = castList.split("\\|");
                ArrayList<String> actorListLow = new ArrayList<String>(Arrays.asList(actorList));
                for (int i = 0; i < actorListLow.size(); i++) {
                    actorListLow.set(i, actorListLow.get(i).toLowerCase());
                    System.out.println(actorListLow);
                    if (actorListLow.get(i).contains(name.toLowerCase())) {
                        boolean add = true;
                        for (String castMember : castMembers) {
                            if (castMember.equals(actorList[i])) {
                                add = false;
                                break;
                            }
                        }
                        if (add) {
                            castMembers.add(actorList[i]);
                        }
                    }
                }
            }
        }

        sortResults(moviesWithWord);
        sortStrings(castMembers); // Implementation in next method

        for (int i = 0; i < castMembers.size(); i++) {
            int order = i + 1;
            System.out.println(order + ". " + castMembers.get(i));
        }

        System.out.println("Which member do you want to learn about?");
        System.out.print("Enter number: ");

        int option = scanner.nextInt();
        scanner.nextLine();

        String selected = castMembers.get(option - 1);
        ArrayList<Movie> list = new ArrayList<Movie>();

        for (Movie movie : moviesWithWord) {
            if (movie.getCast().contains(selected)) {
                list.add(movie);
            }
        }

        System.out.println("What title do you want to check?");

        for (int i = 0; i < list.size(); i++) {
            int order = i + 1;
            System.out.println(order + ". " + list.get(i).getTitle());
        }

        option = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = list.get(option - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n Press enter when your done");
        scanner.nextLine();
    }


    private void searchKeywords()
    {
        System.out.print("Please put in your keyword:");
        String keyword = scanner.nextLine().toLowerCase();
        ArrayList<Movie> moviesWithWord = new ArrayList<Movie>();

        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).getKeywords().contains(keyword)) {
                moviesWithWord.add(movies.get(i));
            }
        }
        sortResults(moviesWithWord);

        for (int i = 0; i < moviesWithWord.size(); i++) {
            int order = i + 1;
            System.out.println(order + ". " + moviesWithWord.get(i).getTitle());
        }

        System.out.println("Which one of the movies do you want to explore: ");
        System.out.print("Enter the number: ");

        int option = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = moviesWithWord.get(option - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n Once your done press enter!");
        scanner.nextLine();
        /* TASK 3: IMPLEMENT ME! */
    }


    private void listGenres()
    {
        {
            ArrayList<String> genre = new ArrayList<>();
            for (Movie movie : movies) {
                boolean addGenre = true;
                String[] genres = movie.getGenres().split("\\|");
                for (String genreList : genres) {
                    for (String allGenres : genre) {
                        if (genreList.equals(allGenres)) {
                            addGenre = false;
                            break;
                        }
                    }
                    if (addGenre) {
                        genre.add(genreList);
                    }
                    addGenre = true;
                }
            }
            sortStrings(genre);
            for (int i = 0; i < genre.size(); i++) {
                int order = i + 1;
                System.out.println(order + ". " + genre.get(i));
            }
            System.out.println("Type tha tnumber your looking for");
            int option = scanner.nextInt();
            scanner.nextLine();
            String selectedGenre = genre.get(option-1);
            ArrayList<Movie> moviesWithGenre = new ArrayList<Movie>();
            for (Movie movie : movies) {
                if (movie.getGenres().contains(selectedGenre)) {
                    moviesWithGenre.add(movie);
                }
            }

            sortResults(moviesWithGenre);
            int count = 1;
            for (Movie moviesList : moviesWithGenre) {
                System.out.println(count + ". " + moviesList);
                count++;
            }
            System.out.println("what Movie do you want?");
            System.out.print("Enter number: ");

            option = scanner.nextInt();
            scanner.nextLine();

            Movie selectedMovie = moviesWithGenre.get(option - 1);

            displayMovieInfo(selectedMovie);

            System.out.println("\n ** Press Enter to Return to Main Menu **");
            scanner.nextLine();
        }
        /* TASK 5: IMPLEMENT ME! */
    }

    private void listHighestRated()
    {
        String title = "";
        double rating = 0.0;
        int inx = 0;
        for(int i = 1; i < 51; i++)
        {
            for(int z = 1; z < movies.size(); z++)
            {
                if(z == 1 && movies.get(z-1).getUserRating() > movies.get(z).getUserRating())
                {
                    title = movies.get(z-1).getTitle();
                    rating = movies.get(z-1).getUserRating();
                    inx = z-1;
                }
                if(z == 1 && movies.get(z-1).getUserRating() < movies.get(z).getUserRating())
                {
                    title = movies.get(z).getTitle();
                    rating = movies.get(z).getUserRating();
                    inx = z;
                }
                if(rating < movies.get(z).getUserRating() && z!=1)
                {
                    title = movies.get(z).getTitle();
                    rating = movies.get(z).getUserRating();
                    inx = z;
                }
            }
            System.out.println(i + ": " + title + " " + rating);
            movies.remove(inx);
        }
    }

    private void listHighestRevenue()
    {
        ArrayList<Movie> allMovies = new ArrayList<>(movies);

        ArrayList<Movie> highestRevenue = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            double highestValue = allMovies.get(0).getRevenue();
            int indexOf = 0;
            for (int p = 0; p < allMovies.size(); p++) {
                if (allMovies.get(p).getRevenue() > highestValue) {
                    highestValue = allMovies.get(p).getRevenue();
                    indexOf = p;
                }
            }
            highestRevenue.add(allMovies.remove(indexOf));
        }

        int count = 1;
        for (Movie moviesList : highestRevenue) {

            System.out.println(count + ". " + moviesList.getTitle() + " (Box Revenue: " + moviesList.getRevenue() + ")");
            count++;

        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int option = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = highestRevenue.get(option - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                // import all cells for a single row as an array of Strings,
                // then convert to ints as needed
                String[] movieFromCSV = line.split(",");

                // pull out the data for this cereal
                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                // create Cereal object to store values
                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);

                // adding Cereal object to the arraylist
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }

    // ADD ANY ADDITIONAL PRIVATE HELPER METHODS you deem necessary

}