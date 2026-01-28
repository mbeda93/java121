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



    JOptionPane - //what does it do?
    URI -
 **/
/*

Types used in this code:

(Add your answers to lab instruction #4 here)

 */

void main() {

    try {
        //constructor method. assigns the output of the getRandomAvatarStream() function to the avatarStream variable
        var avatarStream = getRandomAvatarStream(); //constructor, object type
        showAvatar(avatarStream); //func call
    } catch (IOException | InterruptedException e) {
        //      class method. returns error message if the try block fails
        JOptionPane.showMessageDialog(null, "Failed to load avatar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); //class method
//      type name [.] class method
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
