package mainMenu;

import board.BoardModel;

import javax.swing.*;
import java.awt.*;

public class BoardSizeDialog extends JDialog {
    private int selectedSize;
    private String[] options;

    public BoardSizeDialog(JFrame owner) {
        super(owner, "board Size", true);
        initOptions();
        setupUI();
    }

    private void initOptions(){
        BoardModel.Size[] sizes = BoardModel.Size.values();
        options = new String[sizes.length];
        for(int i = 0; i < sizes.length; i++){
            options[i] = sizes[i].toString();
        }
    }

    private void setupUI(){
        JComboBox<String> sizeOptions = new JComboBox<>(options);
        JButton confirmButton = new JButton("Confirm");

        confirmButton.addActionListener(e -> {
            selectedSize = sizeOptions.getSelectedIndex();
            dispose();
        });

        setLayout(new FlowLayout());
        add(new JLabel("Select the board size:"));
        add(sizeOptions);
        add(confirmButton);
        pack();
        setLocationRelativeTo(getOwner());
    }

    public BoardModel.Size getSelectedSize(){
        return BoardModel.Size.values()[selectedSize];
    }

}
