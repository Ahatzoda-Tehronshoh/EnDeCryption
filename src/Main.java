import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.Vector;

public class Main {
    private JFrame frame;
    private JComboBox<String> methods;
    private JTextField keyField;
    private JPanel panelAtbash;
    private JButton encryptButton;
    private JButton decryptButton;
    private JButton swapButton;
    private JTextArea textAreaInput;
    private JTextArea textAreaOutput;

    private JRadioButton unicodeRadioButton;
    private JRadioButton wordRadioButton;
    private JRadioButton sentenceRadioButton;
    private JRadioButton textRadioButton;

    public Main() {
        frame = new JFrame("Шифровальщик");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Добавление отступов
        String[] str = new String[]{
                "Atbash",
                "Cesar",
                "Vigenere",
                "Playfair",
                "Hill",
                "Vertical"
        };
        methods = new JComboBox<>(str);
        keyField = new JTextField(10);
        keyField.setVisible(false);
        controlPanel.add(new JLabel("Метод:"));
        controlPanel.add(methods);
        JLabel keyLabel = new JLabel("Ключ :");
        keyLabel.setVisible(false);
        controlPanel.add(keyLabel);
        controlPanel.add(keyField);
        panelAtbash = new JPanel();

        unicodeRadioButton = new JRadioButton("Unicode");
        unicodeRadioButton.setSelected(true);
        wordRadioButton = new JRadioButton("Слова");
        sentenceRadioButton = new JRadioButton("Предл.");
        textRadioButton = new JRadioButton("Текст");

        ButtonGroup radioButtonGroup = new ButtonGroup();
        radioButtonGroup.add(unicodeRadioButton);
        radioButtonGroup.add(wordRadioButton);
        radioButtonGroup.add(sentenceRadioButton);
        radioButtonGroup.add(textRadioButton);

        panelAtbash.add(unicodeRadioButton);
        panelAtbash.add(wordRadioButton);
        panelAtbash.add(sentenceRadioButton);
        panelAtbash.add(textRadioButton);

        controlPanel.add(panelAtbash);

        frame.add(controlPanel, BorderLayout.NORTH);

        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Добавление отступов
        textAreaInput = new JTextArea(10, 20);
        textAreaOutput = new JTextArea(10, 20);
        //textAreaInput.setLineWrap(true);
        //textAreaOutput.setLineWrap(true);

        JScrollPane inputScrollPane = new JScrollPane(textAreaInput);
        JScrollPane outputScrollPane = new JScrollPane(textAreaOutput);

        textPanel.add(inputScrollPane);
        textPanel.add(outputScrollPane);

        frame.add(textPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Добавление отступов
        encryptButton = new JButton("Шифровать");
        decryptButton = new JButton("Дешифровать");
        swapButton = new JButton("⁐");
        buttonPanel.add(encryptButton);
        buttonPanel.add(decryptButton);
        buttonPanel.add(swapButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        methods.addActionListener(e -> {
            keyField.setVisible(!Objects.equals(methods.getSelectedItem(), "Atbash"));
            keyLabel.setVisible(!Objects.equals(methods.getSelectedItem(), "Atbash"));
            panelAtbash.setVisible(Objects.equals(methods.getSelectedItem(), "Atbash"));
            frame.pack();
            frame.revalidate();
        });

        // Изменение цвета компонентов
        frame.getContentPane().setBackground(new Color(240, 240, 240));
        methods.setBackground(Color.WHITE);
        textAreaInput.setBackground(Color.WHITE);
        textAreaOutput.setBackground(Color.WHITE);
        encryptButton.setBackground(new Color(50, 150, 250));
        decryptButton.setBackground(new Color(50, 150, 250));


        swapButton.addActionListener(e ->
                textAreaInput.setText(textAreaOutput.getText()));

        encryptButton.addActionListener(e -> {
            Vector<Character> text = new Vector<>();
            for (int i = 0; i < textAreaInput.getText().length(); i++)
                text.add(textAreaInput.getText().charAt(i));

            Vector<Character> key = new Vector<>();
            for (int i = 0; i < keyField.getText().length(); i++)
                key.add(keyField.getText().charAt(i));

            try {
                switch ((String) Objects.requireNonNull(methods.getSelectedItem())) {
                    case "Cesar" -> {
                        int x = (!keyField.getText().equals("")) ? Integer.parseInt(keyField.getText()) : 1;
                        Cesar.encrypt(x, text);
                    }
                    case "Atbash" -> {
                        if (unicodeRadioButton.isSelected())
                            Atbash.unicodeEncrypt(text);
                        else if (wordRadioButton.isSelected())
                            Atbash.wordEncrypt(text);
                        else if (sentenceRadioButton.isSelected())
                            Atbash.sentenceEncrypt(text);
                        else if (textRadioButton.isSelected())
                            Atbash.textEncrypt(text);
                    }
                    case "Vigenere" -> Vigenere.encrypt(key, text);
                    case "Playfair" -> Playfair.encrypt(key, text);
                    case "Hill" -> Hill.encrypt(key, text);
                    case "Vertical" -> Vertical.encrypt(key, text);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                String mes = "Такой ключ не подходит!\n" + ex.getMessage();
                JOptionPane.showMessageDialog(null, mes, "Предупреждение", JOptionPane.WARNING_MESSAGE);
                return;
            }

            StringBuilder sb = new StringBuilder();
            for (Character ch : text) {
                sb.append(ch);
            }
            textAreaOutput.setText(sb.toString());
        });

        decryptButton.addActionListener(e -> {
            Vector<Character> text = new Vector<>();
            for (int i = 0; i < textAreaInput.getText().length(); i++)
                text.add(textAreaInput.getText().charAt(i));

            Vector<Character> key = new Vector<>();
            for (int i = 0; i < keyField.getText().length(); i++)
                key.add(keyField.getText().charAt(i));

            try {
                switch ((String) Objects.requireNonNull(methods.getSelectedItem())) {
                    case "Cesar" -> {
                        int x = (!keyField.getText().equals("")) ? Integer.parseInt(keyField.getText()) : 1;
                        Cesar.decrypt(x, text);
                    }
                    case "Atbash" -> {
                        if (unicodeRadioButton.isSelected())
                            Atbash.unicodeEncrypt(text);
                        else if (wordRadioButton.isSelected())
                            Atbash.wordEncrypt(text);
                        else if (sentenceRadioButton.isSelected())
                            Atbash.sentenceEncrypt(text);
                        else if (textRadioButton.isSelected())
                            Atbash.textEncrypt(text);
                    }
                    case "Vigenere" -> Vigenere.decrypt(key, text);
                    case "Playfair" -> Playfair.decrypt(key, text);
                    case "Hill" -> Hill.decrypt(key, text);
                    case "Vertical" -> Vertical.decrypt(key, text);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                String mes = "Этот шифр не для вас!\n" + ex.getMessage();
                JOptionPane.showMessageDialog(null, mes, "Предупреждение", JOptionPane.WARNING_MESSAGE);
                return;
            }
            StringBuilder sb = new StringBuilder();
            for (Character ch : text) {
                sb.append(ch);
            }
            textAreaOutput.setText(sb.toString());
        });

        frame.pack();

        // Центрирование окна
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (int) ((screenSize.getWidth() - frame.getWidth()) / 2);
        int centerY = (int) ((screenSize.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(centerX, centerY);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}