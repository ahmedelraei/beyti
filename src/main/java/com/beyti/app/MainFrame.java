package com.beyti.app;

import com.beyti.models.Employee;
import com.beyti.models.SalesOffice;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private JPanel mainFramePanel;
    private JTabbedPane tabbedPane1;
    private JTabbedPane tabbedPane2;
    private JTable table1;
    private JTextField textField1;
    private JTextField textField2;
    private JButton addButton;
    private JButton empRegisterButton;
    private JTextField empOfficeIDTextField;
    private JTextField empFirstNameTextField;
    private JTextField empLastNameTextField;
    private JTextField empUsernameTextField;
    private JTextField empPasswordTextField;
    private JTable empTable;
    private JButton addOfficeBtn;
    private JTextField officeLocationTextField;
    private JTable salesOfficeTable;
    private JButton officeUpdateButton;
    private JButton officeDeleteButton;
    private JButton empUpdateButton;
    private JButton empDeleteButton;
    private JRadioButton yesRadioButton;
    private JScrollPane scrollPane1;

    public MainFrame(String title) {
        super(title);
        setContentPane(mainFramePanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1000, 720));

        empTable.setFillsViewportHeight(true);
        salesOfficeTable.setFillsViewportHeight(true);
        populateEmpTable();
        populateSalesOfficeTable();
        pack();
        setVisible(true);
        empRegisterButton.addActionListener(actionEvent ->  {

            try{
                int officeID = Integer.parseInt(empOfficeIDTextField.getText());
                String firstName = empFirstNameTextField.getText();
                String lastName = empLastNameTextField.getText();
                String username = empUsernameTextField.getText();
                String password = empPasswordTextField.getText();
                Employee employee = Employee.create(officeID, firstName, lastName, username, password);
                DefaultTableModel model = (DefaultTableModel) empTable.getModel();
                assert employee != null;
                model.addRow(new Object[]{employee.id, employee.officeNumber, employee.firstName,
                        employee.lastName, employee.username});
                JOptionPane.showMessageDialog(null, "Employee registered successfully!");
                } catch (RuntimeException e) {
                    JOptionPane.showMessageDialog(null, "Error while registering employee!" + e.getMessage());
                }


        });
        addOfficeBtn.addActionListener(actionEvent -> {
            try{
                String location = officeLocationTextField.getText();
                SalesOffice salesOffice = SalesOffice.create(location);
                DefaultTableModel model = (DefaultTableModel) salesOfficeTable.getModel();
                assert salesOffice != null;
                model.addRow(new Object[]{salesOffice.number, salesOffice.location, salesOffice.managerId});
                JOptionPane.showMessageDialog(null, "Office added successfully!");
            } catch (RuntimeException e) {
                JOptionPane.showMessageDialog(null, "Error while adding office!\n" + e.getMessage());
            }
        });
        officeUpdateButton.addActionListener(actionEvent -> {
            int row = salesOfficeTable.getSelectedRow();
            int number = Integer.parseInt(salesOfficeTable.getModel().getValueAt(row, 0).toString());
            String location = salesOfficeTable.getModel().getValueAt(row, 1).toString();
            int managerId = Integer.parseInt(salesOfficeTable.getModel().getValueAt(row, 2).toString());
            if (managerId == 0)
                JOptionPane.showMessageDialog(null, "You can't update an office without a manager!");
            try{
                SalesOffice.update(number, location, managerId);
                JOptionPane.showMessageDialog(null, "Office updated successfully!");
            } catch (RuntimeException e) {
                JOptionPane.showMessageDialog(null, "Error while updating office!\n" + e.getMessage());
            }
        });
        officeDeleteButton.addActionListener(actionEvent -> {
            int row = salesOfficeTable.getSelectedRow();
            int number = Integer.parseInt(salesOfficeTable.getModel().getValueAt(row, 0).toString());
            try{
                SalesOffice.delete(number);
                DefaultTableModel model = (DefaultTableModel) salesOfficeTable.getModel();
                model.removeRow(row);
                JOptionPane.showMessageDialog(null, "Office deleted successfully!");
            } catch (RuntimeException e) {
                JOptionPane.showMessageDialog(null, "Error while deleting office!\n" + e.getMessage());
            }
        });
        empUpdateButton.addActionListener(actionEvent -> {
            int row = empTable.getSelectedRow();
            int id = Integer.parseInt(empTable.getModel().getValueAt(row, 0).toString());
            int officeNumber = Integer.parseInt(empTable.getModel().getValueAt(row, 1).toString());
            String firstName = empTable.getModel().getValueAt(row, 2).toString();
            String lastName = empTable.getModel().getValueAt(row, 3).toString();
            String username = empTable.getModel().getValueAt(row, 4).toString();
            try{
                Employee.update(id, officeNumber, firstName, lastName, username);
                JOptionPane.showMessageDialog(null, "Employee updated successfully!");
            } catch (RuntimeException e) {
                JOptionPane.showMessageDialog(null, "Error while updating employee!\n" + e.getMessage());
            }
        });
        empDeleteButton.addActionListener(actionEvent -> {
            int row = empTable.getSelectedRow();
            int id = Integer.parseInt(empTable.getModel().getValueAt(row, 0).toString());
            try{
                Employee.delete(id);
                DefaultTableModel model = (DefaultTableModel) empTable.getModel();
                model.removeRow(row);
                JOptionPane.showMessageDialog(null, "Employee deleted successfully!");
            } catch (RuntimeException e) {
                JOptionPane.showMessageDialog(null, "Error while deleting employee!\n" + e.getMessage());
            }
        });

    }
    private void populateEmpTable(){
        empTable.setModel(new DefaultTableModel(Employee.getColumns(), 0));
        Employee.get().forEach(employee -> {
            DefaultTableModel model = (DefaultTableModel) empTable.getModel();
            model.addRow(new Object[]{employee.id, employee.officeNumber, employee.firstName,
                    employee.lastName, employee.username});
        });

    }
    private void populateSalesOfficeTable(){
        salesOfficeTable.setModel(new DefaultTableModel(SalesOffice.getColumns(),0));
        SalesOffice.get().forEach(salesOffice -> {
            DefaultTableModel model = (DefaultTableModel) salesOfficeTable.getModel();
            model.addRow(new Object[]{salesOffice.number, salesOffice.location, salesOffice.managerId});
        });

    }
}
