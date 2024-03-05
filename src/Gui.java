import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.Scanner;

public class Gui extends JFrame{
    private JPanel panel;
    private JTextArea txt;
    private File selectedFile;

    public Gui() {
        components();
        menu();
    }
    public void components() {
        setContentPane(panel);
        setTitle("nevim");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(700, 300, 500, 500);
    }

    public void menu() {

        JMenuBar jMenuBar = new JMenuBar();
        setJMenuBar(jMenuBar);

        JMenu jMenu = new JMenu("Options");
        jMenuBar.add(jMenu);

        JMenuItem fileItem = new JMenuItem("File");
        jMenu.add(fileItem);
        fileItem.addActionListener(e -> chooseFile());

        JMenuItem saveItem = new JMenuItem("Save");
        jMenu.add(saveItem);
        saveItem.addActionListener(e -> save(selectedFile));

        JMenuItem saveAsItem = new JMenuItem("Save As");
        jMenu.add(saveAsItem);
        saveAsItem.addActionListener(e -> saveAs());
    }

    public void chooseFile(){
        JFileChooser fc = new JFileChooser(".");
        fc.setFileFilter(new FileNameExtensionFilter("Project files", "txt"));
        int result = fc.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fc.getSelectedFile();
            StringBuilder content = new StringBuilder();
            try(Scanner sc = new Scanner(new BufferedReader(new FileReader(selectedFile)))) {
                while(sc.hasNextLine()) {
                    content.append(sc.nextLine()).append("\n");
                }
                txt.setText(String.valueOf(content));
            }catch(IOException e){
                JOptionPane.showMessageDialog(this, "File not found: "+ e.getLocalizedMessage());
            }
        }

    }

    public void save(File file){
        try(PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)))){
            pw.write(txt.getText());
            JOptionPane.showMessageDialog(this, "File saved successfully.");
        } catch(IOException e) {
            JOptionPane.showMessageDialog(this, "Problem with saving file: "+e.getLocalizedMessage());
        }
    }

    public void saveAs(){
        JFileChooser fc = new JFileChooser(".");
        fc.setFileFilter(new FileNameExtensionFilter("Project files", "txt"));
        int result = fc.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fc.getSelectedFile();
            selectedFile = new File(selectedFile.getAbsolutePath()+".txt");
            save(selectedFile);
        }
    }

    public static void main(String[] args) {
        Gui gui = new Gui();
        gui.setVisible(true);
    }
}
