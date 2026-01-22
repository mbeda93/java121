import java.io.IOException;

/** main loop**/
void main() {
    //initialise variables

    int done = 0;
    List<String> names = new ArrayList<>();
    List<Integer> ages = new ArrayList<>();

//main program loop
    while (done != 2) {
        String name = get_name(); //grab input

        if (name.equals("")) { //check for blank name or done command
            IO.println("Please enter a name. Try again.");
            continue;

        }
        else if (name.equals("!done")) { //check for exit
            done = 2; //stops loop from running again

            for (int i = 0; i < names.size(); i++) {

                String content = (names.get(i) + " is " + ages.get(i)); //save text file output in variable
                try {
                    Files.writeString(
                            Paths.get("output.txt"), //get the textfile path
                            content + "\n",  //generated string, and new line
                            StandardOpenOption.CREATE, //create if it doesnt exist
                            StandardOpenOption.APPEND //write out to the file
                    );
                } catch (IOException e) {
                    IO.println("File write failed.");
                }

            }

            break;
        }

        Integer age = get_age();

        if (age != -1) { //If get_age returns an error (-1), dont run this block
            String printout = "Hello, " + name + ", your age is " + age + "!";
            //Grab those variables, save them into an array or something.
            names.add(name);   // add name to name list/array
            ages.add(age);     // add age to list/array

            IO.println(printout);

            //Now ready for next set of inputs
            done = 1;

        } else if (age == -1) {
            IO.println("Invalid input (must be a number). Try again.");
        }

        }

    }



    //FUNCTIONS//
/** Get someone's name, return to variable to be used later**/
String get_name() {
    return IO.readln("What is your name? ");
}


/** Get the age of someone, return it to the variable to be used later on in the program **/
int get_age() {
    String age_in = IO.readln("What is your age? ");
    try {
         return Integer.parseInt(age_in); //return it to the original assignment of the variable outside the function, no need to do it inside.
    } catch (Exception e) {
        IO.println("Age must be a number!");
        return -1;

    }
}






