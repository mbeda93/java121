import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

//
//Class member (static)
//Instance member (not static)

/**
Types used...

 JOptionPane - interactive dialog boxes
 URI - web links
 HttpClient - handles reauests and responses to/from the internet
 HttpRequest - represents a single request sent thru HttpClient
 HttpResponse - represents a single respose recieved thru HttpClient
 ImageIO - image support for read/write
 JFrame - container for GUI elements
 JLabel - area for short text strings/images
 ImageIcon - create icons from images
 Color - rgb color
 BorderLayout - layout manager

 Packages used...
 javax.swing - lightweight gui components (JOptionPane, JFrame, JLabel, ImageIcon, javax.ImageIO)
 java.awt - ui and graphic element classes (Color, BorderLayout)
 java.net - networking classes (HttpClient/Response/Request)
 java.io - system io
**/

void main() {

    try {
        //constructor method. assigns the output of the getRandomAvatarStream() function to the avatarStream variable
        var avatarStream = getRandomAvatarStream(); // object/reference type variable
        showAvatar(avatarStream); //func call. displays the returned avatar in a window
    } catch (IOException | InterruptedException e) {
        //error message if retrieving the avatar fails.
        JOptionPane.showMessageDialog(null /*prim*/ , "Failed to load avatar: " + e.getMessage() /* object/reference (String) */, "Error" /*Object (String)*/, JOptionPane.ERROR_MESSAGE /*Object Reference, error message func call*/); //class method
    }

}








InputStream getRandomAvatarStream() throws IOException, InterruptedException {
    // Pick a random style
    String[] styles = { "adventurer", "adventurer-neutral", "avataaars", "big-ears", "big-ears-neutral", "big-smile", "bottts", "croodles", "croodles-neutral", "fun-emoji", "icons", "identicon", "initials", "lorelei", "micah", "miniavs", "open-peeps", "personas", "pixel-art", "pixel-art-neutral" };
    var style = styles[(int)(Math.random() * styles.length)];

    // Generate a random seed
    var seed = (int)(Math.random() * 10000);

    // Create an HTTP request for a random avatar
    var uri = URI.create("https://api.dicebear.com/9.x/%s/png?seed=%d".formatted(style, seed));
    var request = HttpRequest.newBuilder(uri).build();

    // Send the request
    try (var client = HttpClient.newHttpClient()) {
        var response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
        return response.body();
    }
}










void showAvatar(InputStream imageStream) {
    JFrame frame = new JFrame("PNG Viewer");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);
    frame.setSize(200, 200);
    frame.getContentPane().setBackground(Color.BLACK);

    try {
        // Load the PNG image
        Image image = ImageIO.read(imageStream);

        // Create a JLabel to display the image
        JLabel imageLabel = new JLabel(new ImageIcon(image));
        frame.add(imageLabel, BorderLayout.CENTER);

    } catch (IOException e) {
        JOptionPane.showMessageDialog(frame, "Failed to load image: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    frame.setVisible(true);
}
