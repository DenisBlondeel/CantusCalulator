package snippet;

public class Snippet {
	if(args.length== 0)
	            System.out.println("no input was given.");
	        if(args.length > 1) {
	            if (!args[1].equals("-o")) {
	                if(!args[1].endsWith(".txt"))
	                    args[1]+= ".txt";
	                PrintStream out = new PrintStream(new FileOutputStream(args[1]));
	                System.setOut(out);
	            }
	        }
	        else
	        {
	            PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
	            System.setOut(out);
	        }
	        if(args[0].equals("experiment")){
	            experiment();
	            return;
	        }
	
	
	        Scanner sc = new Scanner(new File(args[0])).useLocale(Locale.US);
}

