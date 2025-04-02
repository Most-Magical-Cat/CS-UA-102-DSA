import java.io.*;

public class Browser {
    final private UrlInfo homepage;
    
    // Stack for back/forward history
    private Stack<UrlInfo> pageBackStack = new ArrayStack<>();
    private Stack<UrlInfo> pageForwardStack = new ArrayStack<>();
    private UrlInfo currentPage;

    // Stack for back/forward on page
    private Stack<String> frameBackStack = new ArrayStack<>();
    private Stack<String> frameForwardStack = new ArrayStack<>();
    private String currentFrame;

    private TextRenderer renderer;

    public Browser(UrlInfo homepage, int numLinesAtATime) {
        this.homepage = homepage;
        this.renderer = new TextRenderer(numLinesAtATime);
    }

    public static void main(String[] args) throws IOException {
        int numLinesAtATime = Integer.parseInt(args[0]);

        String homepage = "info.cern.ch/hypertext/WWW/index.html";
        UrlInfo homepageUrlInfo = new UrlInfo(homepage, null);

        Browser browser = new Browser(homepageUrlInfo, numLinesAtATime);
        System.out.println(browser.runCommand("HOME"));

        // Being the Read-Eval-Print Loop (or REPL)
        while (true) {
            // Read
            System.out.print("> ");
            String input = new BufferedReader(new InputStreamReader(System.in)).readLine().trim().toUpperCase();
            
            // Eval
            String output = browser.runCommand(input);

            // Print
            System.out.println(output);
        }
    }

    public String runCommand(String command) {
        switch (command.toLowerCase()) {
            case "":
            case "next":
                return showNext();
            case "home":
                return returnHome();
            case "prev":
                return showPrev();
            case "reload":
                return reload();
            case "back":
                return back();
            case "forward":
                return forward();
            case "help":
                return showHelp();
            case "links":
                return links();
            case "exit":
            case "quit":
                System.exit(0);
                return "";
            default:
                if (isNumber(command)) {
                    if (renderer.links.size() <= Integer.parseInt(command)) {return "Invalid link number";}
                    else {
                        pageBackStack.push(currentPage);
                        UrlInfo urlInfo = UrlInfo.navigate(currentPage.path, renderer.links.get(Integer.parseInt(command)));
                        frameForwardStack.clear();
                        frameBackStack.clear();
                        currentFrame = null;
                        renderer.newPage(urlInfo);
                        currentPage = urlInfo;
                        currentFrame = renderer.nextFrame();
                        return currentFrame;
                    }
                } else {
                    return "Invalid command. Type HELP for a list of commands.";
                }
        }
    }

    private String formatLink(int number, String link) {
        return String.format("[%d] %s", number, link);
    }

    // Show help menu
    private String showHelp() {
        StringBuffer sb = new StringBuffer();
        sb.append("Commands Available:\n");
        sb.append("HOME: Go to the home page\n");
        sb.append("NEXT (or no command): Go to the next frame of text.\n");
        sb.append("PREV: Re-render the previous frame of the current page.\n");
        sb.append("<number>: Go to the link associated with the number.\n");
        sb.append("BACK: Go back to the previous page.\n");
        sb.append("FORWARD: Go forward to the next page.\n");
        sb.append("LINKS: Show the list of links available on the current page.\n");
        sb.append("HELP: Show this help menu.\n");

        return sb.toString();
    }

    private boolean isNumber(String command) {
        try {
            Integer.parseInt(command);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String returnHome(){
        renderer.newPage(homepage);
        currentPage = homepage;
        currentFrame = renderer.nextFrame();
        return currentFrame;
    }

    private String showNext(){
        if (renderer.hasNextFrame()) {
            frameBackStack.push(currentFrame);
            if (frameForwardStack.isEmpty()) {
                currentFrame = renderer.nextFrame();
            }
            else {currentFrame = frameForwardStack.pop();}
            return currentFrame;
        }
        else {return "No next frame to be shown.";
        }
    }

    private String showPrev(){
        if (frameBackStack.isEmpty()) {return "No previous frame to be shown.";
        }
        else {
            frameForwardStack.push(currentFrame);
            currentFrame = frameBackStack.pop();
            return currentFrame;
        }
    }

    private String reload(){
        frameBackStack.clear();
        frameForwardStack.clear();
        currentFrame = null;
        renderer.newPage(currentPage);
        currentFrame = renderer.nextFrame();
        return currentFrame;
    }

    private String back(){
        if (pageBackStack.isEmpty()) {return "No page to go back to";}
        else {
            pageForwardStack.push(currentPage);
            currentPage = pageBackStack.pop();
            renderer.newPage(currentPage);
            currentFrame = renderer.nextFrame();
            return currentFrame;
        }
    }

    private String forward(){
        if (pageForwardStack.isEmpty()) {
            return "No page to go forward to";
        }
        else {
            pageBackStack.push(currentPage);
            currentPage = pageForwardStack.pop();
            renderer.newPage(currentPage);
            currentFrame = renderer.nextFrame();
            return currentFrame;
        }
    }

    private String links(){
        StringBuffer s = new StringBuffer();
        for (int i = 0; i < renderer.links.size(); i++) {
            s.append(formatLink(i, renderer.links.get(i)));
        }
        return s.toString();
    }
}
