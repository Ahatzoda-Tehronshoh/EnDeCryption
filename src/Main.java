import javax.swing.*;
import java.awt.*;
import java.math.BigInteger;
import java.util.Objects;
import java.util.Vector;

public class Main {
    private JFrame frame;
    private JComboBox<String> methods;
    private JTextField keyField;
    private JButton encryptButton;
    private JButton decryptButton;
    private JButton swapButton;
    private JTextArea textAreaInput;
    private JTextArea textAreaOutput;

    private JPanel panelAtbash;
    private JPanel panelDiffieHellman;
    private JPanel panelRSA;

    private JRadioButton unicodeRadioButton;
    private JRadioButton wordRadioButton;
    private JRadioButton sentenceRadioButton;
    private JRadioButton textRadioButton;

    public Main() {
        frame = new JFrame("Шифровальщик");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Добавление отступов
        String[] str = new String[]{
                "Atbash",
                "Cesar",
                "Vigenere",
                "Playfair",
                "Hill",
                "Vertical",
                "Algebra Matrix",
                "DiffieHellman",
                "RSA"
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
        panelDiffieHellman = new JPanel();
        panelDiffieHellman.setVisible(false);
        panelRSA = new JPanel();
        panelRSA.setVisible(false);

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

        panelRSA.setLayout(new BoxLayout(panelRSA, BoxLayout.X_AXIS));
        panelRSA.add(new JLabel("q:"));
        JTextField qRSA = new JTextField();
        panelRSA.add(qRSA);
        panelRSA.add(new JLabel("Fi:"));
        JTextField fiRSA = new JTextField();
        fiRSA.setEditable(false);
        panelRSA.add(fiRSA);
        panelRSA.add(new JLabel("e:"));
        JTextField eRSA = new JTextField();
        panelRSA.add(eRSA);
        eRSA.setEditable(false);
        panelRSA.add(new JLabel("d:"));
        JTextField dRSA = new JTextField();
        panelRSA.add(dRSA);

        panelDiffieHellman.setLayout(new GridLayout(6, 2));

        panelDiffieHellman.add(new JLabel("q:"));
        JPanel qPanel = new JPanel();
        qPanel.setLayout(new BoxLayout(qPanel, BoxLayout.Y_AXIS));
        JTextField qDH = new JTextField();
        qDH.setEditable(false);
        qPanel.add(qDH);
        JButton qDHButton = new JButton("Generate Q");
        qPanel.add(qDHButton);
        qDHButton.addActionListener(e -> {
            BigInteger q = DiffieHellman.primitiveRoot(new BigInteger(keyField.getText()));
            qDH.setText(q.toString());
            System.out.println(q);
        });
        panelDiffieHellman.add(qPanel);
        panelDiffieHellman.add(new JLabel("x1:"));
        JTextField x1DH = new JTextField();
        panelDiffieHellman.add(x1DH);
        panelDiffieHellman.add(new JLabel("y1:"));
        JTextField y1DH = new JTextField();
        panelDiffieHellman.add(y1DH);
        panelDiffieHellman.add(new JLabel("x2:"));
        JTextField x2DH = new JTextField();
        panelDiffieHellman.add(x2DH);
        panelDiffieHellman.add(new JLabel("y2:"));
        JTextField y2DH = new JTextField();
        panelDiffieHellman.add(y2DH);
        panelDiffieHellman.add(new JLabel("k:"));
        JTextField kDH = new JTextField();
        panelDiffieHellman.add(kDH);

        frame.add(controlPanel);

        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Добавление отступов
        textAreaInput = new JTextArea(10, 20);
        textAreaOutput = new JTextArea(10, 20);

        JScrollPane inputScrollPane = new JScrollPane(textAreaInput);
        JScrollPane outputScrollPane = new JScrollPane(textAreaOutput);

        textPanel.add(inputScrollPane);
        textPanel.add(outputScrollPane);

        panelDiffieHellman.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelRSA.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        frame.add(panelRSA);
        frame.add(panelDiffieHellman);
        frame.add(textPanel);

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

            panelDiffieHellman.setVisible(Objects.equals(methods.getSelectedItem(), "DiffieHellman"));
            textAreaInput.setVisible(!Objects.equals(methods.getSelectedItem(), "DiffieHellman"));
            textAreaOutput.setVisible(!Objects.equals(methods.getSelectedItem(), "DiffieHellman"));

            panelRSA.setVisible(Objects.equals(methods.getSelectedItem(), "RSA"));

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
                    case "Algebra Matrix" -> AlgebraMatrix.encrypt(key, text);
                    case "RSA" -> {
                        int p = Integer.parseInt(keyField.getText());
                        int q = Integer.parseInt(qRSA.getText());

                        BigInteger[] ed = RSA.generateED(p, q);
                        eRSA.setText(ed[0]+"");
                        dRSA.setText(ed[1]+"");
                        fiRSA.setText((p-1)*(q-1)+"");

                        RSA.encrypt(p, q, ed[0], text);
                    }
                    case "DiffieHellman" -> {
                        BigInteger a = new BigInteger(keyField.getText());
                        if (!qDH.getText().isEmpty()) {
                            BigInteger q = new BigInteger(qDH.getText());
                            BigInteger y1 = null, y2 = null;
                            if (!x1DH.getText().isEmpty()) {
                                y1 = a.modPow(new BigInteger(x1DH.getText()), q);
                                y1DH.setText(y1.toString());
                            } else
                                JOptionPane.showMessageDialog(panelDiffieHellman, "Error!", "You forgot to enter 0 < X1 < Q", JOptionPane.ERROR_MESSAGE);

                            if (!x2DH.getText().isEmpty()) {
                                y2 = a.modPow(new BigInteger(x2DH.getText()), q);
                                y2DH.setText(y2.toString());
                            } else
                                JOptionPane.showMessageDialog(panelDiffieHellman, "Error!", "You forgot to enter 0 < X2 < Q", JOptionPane.ERROR_MESSAGE);

                            if (y1 != null && y2 != null) {
                                BigInteger k1 = y2.modPow(new BigInteger(x1DH.getText()), q);
                                BigInteger k2 = y1.modPow(new BigInteger(x2DH.getText()), q);

                                System.out.println(k1 + "; k2 = " + k2);

                                if (k1.toString().equals(k2.toString()))
                                    kDH.setText(k1.toString());
                                else
                                    JOptionPane.showMessageDialog(panelDiffieHellman, "Error!", "Your first and second user's K don't equals!", JOptionPane.ERROR_MESSAGE);
                            }
                        } else
                            JOptionPane.showMessageDialog(panelDiffieHellman, "Error!", "You forgot to generate Q;", JOptionPane.ERROR_MESSAGE);
                    }
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
                    case "Algebra Matrix" -> AlgebraMatrix.decrypt(key, text);
                    case "RSA" -> {
                        int p = Integer.parseInt(keyField.getText());
                        int q = Integer.parseInt(qRSA.getText());
                        String d = dRSA.getText();
                        RSA.decrypt(p, q, new BigInteger(d), text);
                    }
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