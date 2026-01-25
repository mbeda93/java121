import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;




/**
 1. [.] uses
  - JOptionPane.showMessageDialog(parentComponent, message, title, messageType, null) -> Class Method
  - JOptionPane.ERROR_MESSAGE -> Class Variable (conveniently highlighted in purple)

 2. Function/Method calls:
    JOptionPane.showMessageDialog()
    - This returns an error message if the try doesnt work out (no data from the server where this program is connecting to)
    - Tried to run this with my LAN disconnected, the error message never appreared.
 3. Constructor methods:
    var avatarStream = getRandomAvatarStream()
    - Calls the getRandomAvatarStream function in the file, and grabs a random avatar via the dicebear api, and returns that data stream as the variable.

 **/
void main() {

    try {
        var avatarStream = getRandomAvatarStream(); //constructor, object type
        showAvatar(avatarStream); //func call
    } catch (IOException | InterruptedException e) {
        JOptionPane.showMessageDialog(null, "Failed to load avatar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); //class method

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
