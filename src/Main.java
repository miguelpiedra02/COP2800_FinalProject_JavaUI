import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

class VMPGUI implements ActionListener {
    private static JButton button1;

    public static void main(String[] args) {
        VMPGUI gui = new VMPGUI(button1);
    }

    private final JFrame frame;
    private final JButton button2;
    private final JButton loginButton;

    public VMPGUI(JButton button1) {
        VMPGUI.button1 = button1;
        frame = new JFrame("VMP Enterprises");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(0, 0, 128));
//Part 2
        JPanel panel = new JPanel(new GridLayout(5, 1));
        panel.setBackground(Color.white);

        JLabel label = new JLabel("Welcome to VMP Enterprises");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label);

        frame.add(panel);

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        panel.add(loginButton);

        button2 = new JButton("Trucker Registration");
        button2.addActionListener(this);

        panel.add(button2);

        frame.add(panel);
        frame.setVisible(true);
    }
                //PART 2 PRESENTATION

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == loginButton) {
            String username = JOptionPane.showInputDialog(frame, "Enter username:");
            String password = JOptionPane.showInputDialog(frame, "Enter password:");

            if (username.equals("admin") && password.equals("office2")) {

                JOptionPane.showMessageDialog(frame, "Login successful!");
/*
                                        MANAGER PANEL
 */
                // Create a new frame for the manager page
                JFrame managerFrame = new JFrame("Manager Page");
                managerFrame.setSize(400, 400);
                managerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

// Create a new panel for the manager page
                JPanel managerPanel = new JPanel(new GridLayout(5, 1));

// Add buttons to the manager panel for changing the payroll amounts
                JButton mechanicPayrollButton = new JButton("Change Mechanic Payroll");
                mechanicPayrollButton.addActionListener(e -> {
                    String mechanicName = JOptionPane.showInputDialog(managerFrame, "Enter mechanic name:");
                    String newPayrollAmount = JOptionPane.showInputDialog(managerFrame, "Enter new mechanic payroll amount:");

                    try {
                        FileWriter writer = new FileWriter("MechanicWeekly.txt", true);
                        writer.write(mechanicName + ": $" + newPayrollAmount + "\n");
                        writer.write("Date: " + java.time.LocalDate.now() + "\n");

                        writer.close();
                        // Read the current mechanic payroll information
                        try {
                            FileReader reader = new FileReader("MechanicWeekly.txt");
                            BufferedReader bufferedReader = new BufferedReader(reader);
                            StringBuilder stringBuilder = new StringBuilder();
                            String line;
                            while ((line = bufferedReader.readLine()) != null) {
                                String[] parts = line.split(":");
                                mechanicName = parts[0].trim();
                                String mechanicPayroll = parts[1].trim();
                                stringBuilder.append(mechanicName).append(": ").append(mechanicPayroll).append("\n");
                            }
                            reader.close();
                            String newPayrollInfo = stringBuilder.toString();

                            // Write the updated mechanic payroll information to the file
                            writer = new FileWriter("MechanicWeekly.txt");
                            writer.write(newPayrollInfo);
                            writer.close();

                            JOptionPane.showMessageDialog(managerFrame, "Mechanic payroll updated successfully!");
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }

                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                });

                managerPanel.add(mechanicPayrollButton);
                managerFrame.add(managerPanel);
                managerFrame.setVisible(true);

                // Create a new button for adding expenses
                JButton expensesButton = new JButton("Add Expenses");
                expensesButton.addActionListener(e -> {
                    // Prompt the user to enter the mechanic's name and the amount for the week
                    String name= JOptionPane.showInputDialog(frame, "Enter mechanic name:");
                    String expenseAmount = JOptionPane.showInputDialog(frame, "Enter expense amount for the week:");
// Write the expense information to a file
                    try {
                        FileWriter writer = new FileWriter("Expenses.txt", true);
                        writer.write(name + ": $" + expenseAmount + "\n");
                        writer.write("Date: " + java.time.LocalDate.now() + "\n");
                        writer.close();

                        JOptionPane.showMessageDialog(frame, "Expenses saved successfully!");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });

                managerPanel.add(expensesButton);

                JButton officePayrollButton = new JButton("Change Office Payroll");
                officePayrollButton.addActionListener(e -> {
                    String newPayrollAmount = JOptionPane.showInputDialog(frame, "Enter name of employee, along with their payroll amount.");
                    try {
                        FileWriter writer = new FileWriter("OfficePayroll.txt");
                        writer.write("New payroll amount: " + newPayrollAmount + "\n");
                        writer.write("Date: " + java.time.LocalDate.now() + "\n");
                        writer.close();

                        JOptionPane.showMessageDialog(frame, "Office payroll updated successfully!");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                managerPanel.add(officePayrollButton);

                // Add the manager panel to the manager frame
                managerFrame.add(managerPanel);

                // Set the manager frame to be visible
                managerFrame.setVisible(true);

                // Disable the buttons in the main panel
                button2.setEnabled(false);
                loginButton.setEnabled(false);
                //PART 3 END
            } else {
                JOptionPane.showMessageDialog(frame, "Incorrect username or password.");
            }

        } else if (event.getSource() == button2) {
            String name = JOptionPane.showInputDialog(frame, "Please enter your name:");

            if (name == null || name.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Name cannot be empty.");
                return;
            }

            showLoading();
            JOptionPane.showMessageDialog(frame, "Trucker Registration");

            // Ask waste management questions and save responses to a file
            int response = JOptionPane.showConfirmDialog(frame, "Do you have a valid CDL license?");
            saveToFile("Name: " + name + "\nCDL License: ", response);

            response = JOptionPane.showConfirmDialog(frame, "Are you familiar with DOT regulations?");
            saveToFile("DOT Regulations: ", response);

            response = JOptionPane.showConfirmDialog(frame, "Are you experienced with waste management?");
            saveToFile("Waste Management Experience: ", response);

            JOptionPane.showMessageDialog(frame, "Registration complete!");
        }
    }

    private void showLoading() {
    }

    private void saveToFile(String question, int response) {
        try {
            FileWriter writer = new FileWriter("trucker_registration.txt", true);
            writer.write(question);


            if (response == JOptionPane.YES_OPTION) {
                writer.write("Yes\n");
            } else {
                writer.write("No\n");
            }

            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }}