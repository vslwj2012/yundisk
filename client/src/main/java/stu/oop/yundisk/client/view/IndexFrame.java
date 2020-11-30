package stu.oop.yundisk.client.view;


import stu.oop.yundisk.client.utils.YunUtils;
import stu.oop.yundisk.servercommon.entity.File;
import stu.oop.yundisk.servercommon.entity.User;
import stu.oop.yundisk.servercommon.model.MethodValue;
import stu.oop.yundisk.servercommon.model.Request;
import stu.oop.yundisk.servercommon.model.Response;
import stu.oop.yundisk.servercommon.model.ResponseMessage;
import stu.oop.yundisk.servercommon.properties.BaseProperties;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.*;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class IndexFrame extends JFrame {

    private JTextField nicknameField, spacesizeField;
    private JPanel contentPane;
    private JTable image_table, word_table, video_table, music_table, other_table;
    private DefaultTableModel image_tablemodel, word_tablemodel, video_tablemodel, music_tablemodel, other_tablemodel;
    private JTable nowUploadList_table, nowDownloadList_table, alreadyUploadList_table, alreadyDownloadList_table;
    private DefaultTableModel nowUploadList_tablemodel, nowDownloadList_tablemodel, alreadyUploadList_tablemodel, alreadyDownloadList_tablemodel;
    private JScrollPane scrollPane, left_panel;
    private JTree allFiles;
    private DefaultTreeModel allFiles_model;
    private DefaultMutableTreeNode allFiles_root, mySource;
    private JList leftSource_list, leftUpload_list, leftDownload_list;

    private ArrayList<Component> leftSourceList, leftUploadList, leftDownloadList;

    private User user;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private JButton uploadButton, downloadButton;
    private JPopupMenu nowUploadList_RightKeyMenu;//上传列表右键菜单
    private JMenuItem continueUpload, pauseUpload;//上传列表右键菜单元素
    private JPopupMenu allFileTree_RightKeyMenu;//所有文件右键菜单
    private JMenuItem deleteFile;//所有文件菜单元素
    private Set<stu.oop.yundisk.servercommon.entity.File> filesSet;
    private Object[] image_table_column = {"文件名", "文件类型", "文件大小"};//
    private Object[] nowList_table_column = {"文件名", "速度", "进度"};

    private int nowUpload_row = 0;//本次运行正在上传文件的数量
    private int alreadyUpload_row = 0;//本次运行已上传文件数量
    private int nowDownload_row = 0;//本次运行正在下载文件数量
    private int alreadyDownload_row = 0;//本次运行已下载文件数量

    public IndexFrame() {
    }

    private DefaultMutableTreeNode yourChoiceInTree;//选择的要下载的文件
    private String nowNickName;
    private int choiceRow;//选择的行数，用于行监听右键菜单定位

    /**
     * Create the frame.
     */
    public IndexFrame(User user, ObjectOutputStream out, ObjectInputStream in) {
        this.user = user;
        this.out = out;
        this.in = in;
        this.filesSet = user.getFileSet();

        //渲染
        DefaultListCellRenderer renderer = new DefaultListCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(JLabel.LEFT);


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 650, 400);
        setTitle("Yun");
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel logo = new JLabel("");
        logo.setBounds(0, 0, 95, 50);
        contentPane.add(logo);

        JPanel panel = new JPanel();
        panel.setBounds(95, 0, 539, 50);
        contentPane.add(panel);
        panel.setLayout(null);

        JToggleButton myDisk = new JToggleButton("\u6211\u7684\u7F51\u76D8");
        myDisk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                left_panel.setViewportView(leftSource_list);
                scrollPane.setViewportView(allFiles);
                downloadButton.setEnabled(false);
            }
        });
        myDisk.setSelected(true);
        myDisk.setBounds(5, 5, 81, 35);
        panel.add(myDisk);

        JToggleButton uploadList = new JToggleButton("\u4E0A\u4F20\u5217\u8868");
        uploadList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                left_panel.setViewportView(leftUpload_list);
                scrollPane.setViewportView(nowUploadList_table);
                downloadButton.setEnabled(false);
            }
        });
        uploadList.setBounds(110, 5, 81, 35);
        panel.add(uploadList);

        JToggleButton downloadList = new JToggleButton("\u4E0B\u8F7D\u5217\u8868");
        downloadList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                left_panel.setViewportView(leftDownload_list);
                scrollPane.setViewportView(nowDownloadList_table);
                downloadButton.setEnabled(false);
            }
        });
        downloadList.setBounds(215, 5, 81, 35);
        panel.add(downloadList);

        ButtonGroup btnG1 = new ButtonGroup();
        btnG1.add(myDisk);
        btnG1.add(uploadList);
        btnG1.add(downloadList);

        JLabel lblNewLabel = new JLabel("\u7528\u6237\uFF1A");
        lblNewLabel.setBounds(306, 5, 36, 35);
        panel.add(lblNewLabel);

        nowNickName = user.getNickname();
        nicknameField = new JTextField(nowNickName);
        nicknameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent arg0) {
                nicknameField.setBackground(new Color(255, 255, 255));
                nicknameField.setBorder(BorderFactory.createLineBorder(new Color(130, 135, 144)));
            }

            @Override
            public void focusLost(FocusEvent e) {
                nicknameField.setBackground(new Color(240, 240, 240));
                nicknameField.setBorder(BorderFactory.createLineBorder(new Color(240, 240, 240)));

                String nick = nicknameField.getText();
                if (nick.equals("")) {
                    nicknameField.setText(nowNickName);
                    return;
                }//当重新输入的nickname为空时nickname不变
                if (nick.equals(nowNickName)) {
                    return;
                }
                nowNickName = nick;

                user.setNickname(nick);
                Request request = new Request();
                request.setMethodValue(MethodValue.UPDATE_NICKNAME);
                Map<String, Object> params = new HashMap<>();
                params.put("newNickname", user.getNickname());
                params.put("username", user.getUsername());
                request.setParams(params);

                try {
                    out.writeObject(request);
                    out.flush();
                    out.reset();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        nicknameField.setBounds(339, 12, 86, 21);
        nicknameField.setBorder(BorderFactory.createLineBorder(new Color(240, 240, 240)));
        nicknameField.setBackground(new Color(240, 240, 240));
        panel.add(nicknameField);
        nicknameField.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("\u7A7A\u95F4\uFF1A");
        lblNewLabel_1.setBounds(429, 15, 45, 15);
        panel.add(lblNewLabel_1);

        String nowspace = String.format("%.2f", (double) user.getSpace() / 1073741824);
        spacesizeField = new JTextField(nowspace + "/10.0G");
        spacesizeField.setEnabled(false);
        spacesizeField.setBounds(464, 12, 65, 21);
        spacesizeField.setBorder(BorderFactory.createLineBorder(new Color(240, 240, 240)));
        panel.add(spacesizeField);
        spacesizeField.setColumns(10);

        JPanel panel_1 = new JPanel();
        panel_1.setBounds(0, 50, 634, 311);
        contentPane.add(panel_1);
        panel_1.setLayout(null);

        ButtonGroup btnG2 = new ButtonGroup();

        left_panel = new JScrollPane();
        left_panel.setBounds(0, 0, 95, 245);
        left_panel.setBorder(BorderFactory.createLineBorder(new Color(240, 240, 240)));
        panel_1.add(left_panel);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(95, 0, 539, 311);
        scrollPane.setBackground(Color.WHITE);
        panel_1.add(scrollPane);

        //我的云盘模块
        leftSource_list = new JList();
        leftSource_list.setBounds(0, 0, 95, 311);
        leftSource_list.setBackground(new Color(240, 240, 240));
        leftSource_list.setFont(new Font("等线", Font.BOLD, 15));
        leftSource_list.setFixedCellHeight(30);
        leftSource_list.setCellRenderer(renderer);
        leftSource_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == 1 && e.getClickCount() == 1) {
                    int index = leftSource_list.getSelectedIndex();
                    scrollPane.setViewportView(leftSourceList.get(index));
                    downloadButton.setEnabled(false);

                    if (index == 1) {
                        addRowIntoTable(image_tablemodel, "jpg", "png", "gif", "jpeg");
                    }
                    if (index == 2) {
                        addRowIntoTable(word_tablemodel, "doc", "docx", "txt", "ppt", "pptx", "xlsx");
                    }
                    if (index == 3) {
                        addRowIntoTable(video_tablemodel, "rmvb", "mp4", "avi", "mkv");
                    }
                    if (index == 4) {
                        addRowIntoTable(music_tablemodel, "mp3");
                    }
                    if (index == 5) {
                        Set<File> others = new HashSet<>();
                        for (File files : filesSet) {
                            if (!files.getFiletype().equals("mp3") && !files.getFiletype().equals("rmvb") && !files.getFiletype().equals("mp4") && !files.getFiletype().equals("avi") && !files.getFiletype().equals("mkv")
                                    && !files.getFiletype().equals("doc") && !files.getFiletype().equals("docx") && !files.getFiletype().equals("txt") && !files.getFiletype().equals("ppt")
                                    && !files.getFiletype().equals("jpg") && !files.getFiletype().equals("png") && !files.getFiletype().equals("gif") && !files.getFiletype().equals("jpeg")
                                    && !files.getFiletype().equals("pptx") && !files.getFiletype().equals("xlsx")) {
                                others.add(files);
                            }
                        }
                        if (other_tablemodel.getRowCount() != others.size()) {
                            for (File files : others) {
                                other_tablemodel.addRow(image_table_column);
                                other_tablemodel.setValueAt(files.getFilename(), other_tablemodel.getRowCount() - 1, 0);
                                other_tablemodel.setValueAt(files.getFiletype(), other_tablemodel.getRowCount() - 1, 1);
                                String filesize = null;
                                if (files.getFilesize() / 1024 < 1024) {
                                    filesize = files.getFilesize() / 1024 + "K";
                                } else {
                                    filesize = String.format("%.1f", files.getFilesize() / 1048576.0) + "M";
                                }
                                other_tablemodel.setValueAt(filesize, other_tablemodel.getRowCount() - 1, 2);
                            }
                        }
                    }
                }
            }
        });

        Set<String> functions = new LinkedHashSet<>();
        functions.add("<html><font size=3>全部文件<p></html>");
        functions.add("<html><font size=3>图片<p></html>");
        functions.add("<html><font size=3>文档<p></html>");
        functions.add("<html><font size=3>视频<p></html>");
        functions.add("<html><font size=3>音乐<p></html>");
        functions.add("<html><font size=3>其他<p></html>");
        leftSource_list.setListData(functions.toArray());
        left_panel.setViewportView(leftSource_list);

        allFiles_root = new DefaultMutableTreeNode("全部文件");
        mySource = new DefaultMutableTreeNode("我的资源");
        allFiles_root.insert(mySource, 0);
        allFiles = new JTree(allFiles_root);
        allFiles.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                yourChoiceInTree = (DefaultMutableTreeNode) allFiles.getSelectionPath().getLastPathComponent();
                if (e.getButton() == 1) {
                    if (yourChoiceInTree.isLeaf()) {
                        downloadButton.setEnabled(true);
                    }
                    if (!yourChoiceInTree.isLeaf()) {
                        downloadButton.setEnabled(false);
                    }
                } else if (e.getButton() == 3) {
                    allFileTree_RightKeyMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
        allFiles_model = (DefaultTreeModel) allFiles.getModel();
        if (filesSet != null) {
            for (stu.oop.yundisk.servercommon.entity.File f : filesSet) {
                if (-1 == f.getUpstatus()) {
                    allFiles_model.insertNodeInto(new DefaultMutableTreeNode(f.getFilename()), mySource, mySource.getChildCount());
                }
            }
        }

        allFiles.setRootVisible(false);

        image_tablemodel = new DefaultTableModel(image_table_column, 0);
        image_table = new JTable(image_tablemodel);
        image_table.setRowHeight(28);
        image_table.getColumnModel().getColumn(0).setPreferredWidth(301);
        image_table.getColumnModel().getColumn(0).setHeaderRenderer(tcr);
        image_table.getColumnModel().getColumn(1).setPreferredWidth(119);
        image_table.getColumnModel().getColumn(1).setHeaderRenderer(tcr);
        image_table.getColumnModel().getColumn(2).setPreferredWidth(119);
        image_table.getColumnModel().getColumn(2).setHeaderRenderer(tcr);
        image_table.setShowGrid(false);

        word_tablemodel = new DefaultTableModel(image_table_column, 0);
        word_table = new JTable(word_tablemodel);
        word_table.setRowHeight(28);
        word_table.getColumnModel().getColumn(0).setPreferredWidth(301);
        word_table.getColumnModel().getColumn(0).setHeaderRenderer(tcr);
        word_table.getColumnModel().getColumn(1).setPreferredWidth(119);
        word_table.getColumnModel().getColumn(1).setHeaderRenderer(tcr);
        word_table.getColumnModel().getColumn(2).setPreferredWidth(119);
        word_table.getColumnModel().getColumn(2).setHeaderRenderer(tcr);
        word_table.setShowGrid(false);

        video_tablemodel = new DefaultTableModel(image_table_column, 0);
        video_table = new JTable(video_tablemodel);
        video_table.setRowHeight(28);
        video_table.getColumnModel().getColumn(0).setPreferredWidth(301);
        video_table.getColumnModel().getColumn(0).setHeaderRenderer(tcr);
        video_table.getColumnModel().getColumn(1).setPreferredWidth(119);
        video_table.getColumnModel().getColumn(1).setHeaderRenderer(tcr);
        video_table.getColumnModel().getColumn(2).setPreferredWidth(119);
        video_table.getColumnModel().getColumn(2).setHeaderRenderer(tcr);
        video_table.setShowGrid(false);

        music_tablemodel = new DefaultTableModel(image_table_column, 0);
        music_table = new JTable(music_tablemodel);
        music_table.setRowHeight(28);
        music_table.getColumnModel().getColumn(0).setPreferredWidth(301);
        music_table.getColumnModel().getColumn(0).setHeaderRenderer(tcr);
        music_table.getColumnModel().getColumn(1).setPreferredWidth(119);
        music_table.getColumnModel().getColumn(1).setHeaderRenderer(tcr);
        music_table.getColumnModel().getColumn(2).setPreferredWidth(119);
        music_table.getColumnModel().getColumn(2).setHeaderRenderer(tcr);
        music_table.setShowGrid(false);

        other_tablemodel = new DefaultTableModel(image_table_column, 0);
        other_table = new JTable(other_tablemodel);
        other_table.setRowHeight(28);
        other_table.getColumnModel().getColumn(0).setPreferredWidth(301);
        other_table.getColumnModel().getColumn(0).setHeaderRenderer(tcr);
        other_table.getColumnModel().getColumn(1).setPreferredWidth(119);
        other_table.getColumnModel().getColumn(1).setHeaderRenderer(tcr);
        other_table.getColumnModel().getColumn(2).setPreferredWidth(119);
        other_table.getColumnModel().getColumn(2).setHeaderRenderer(tcr);
        other_table.setShowGrid(false);

        scrollPane.setViewportView(allFiles);

        leftSourceList = new ArrayList<>();
        leftSourceList.add(allFiles);

        leftSourceList.add(image_table);
        leftSourceList.add(word_table);
        leftSourceList.add(video_table);
        leftSourceList.add(music_table);
        leftSourceList.add(other_table);

        //上传列表模块
        leftUpload_list = new JList();
        leftUpload_list.setBounds(0, 0, 95, 311);
        leftUpload_list.setBackground(new Color(240, 240, 240));
        leftUpload_list.setFont(new Font("等线", Font.BOLD, 15));
        leftUpload_list.setFixedCellHeight(30);
        leftUpload_list.setCellRenderer(renderer);
        leftUpload_list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                scrollPane.setViewportView(leftUploadList.get(leftUpload_list.getSelectedIndex()));
                downloadButton.setEnabled(false);
            }
        });
        Set<String> functions2 = new LinkedHashSet<>();
        functions2.add("<html><font size=3>正在上传<p></html>");
        functions2.add("<html><font size=3>已上传<p></html>");
        leftUpload_list.setListData(functions2.toArray());
        leftUpload_list.isSelectedIndex(0);

        nowUploadList_tablemodel = new DefaultTableModel(nowList_table_column, 0);
        nowUploadList_table = new JTable(nowUploadList_tablemodel);
        nowUploadList_table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                choiceRow = nowUploadList_table.getSelectedRow();
                if (e.getButton() == 3) {
                    nowUploadList_RightKeyMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
        nowUploadList_table.setRowHeight(28);
        nowUploadList_table.getColumnModel().getColumn(0).setPreferredWidth(301);
        nowUploadList_table.getColumnModel().getColumn(0).setHeaderRenderer(tcr);
        nowUploadList_table.getColumnModel().getColumn(1).setPreferredWidth(119);
        nowUploadList_table.getColumnModel().getColumn(1).setHeaderRenderer(tcr);
        nowUploadList_table.getColumnModel().getColumn(2).setPreferredWidth(119);
        nowUploadList_table.getColumnModel().getColumn(2).setHeaderRenderer(tcr);
        //nowUploadList_table.getColumnModel().getColumn(2).setCellRenderer(new ProgressCellRender());
        nowUploadList_table.setShowGrid(false);
        //在从服务器获取的文件集合中查询没有上传完成的文件并添加到正在上传列表
        if (filesSet != null) {
            for (stu.oop.yundisk.servercommon.entity.File files : filesSet) {
                if (files.getUpstatus() > -1) {
                    nowUploadList_tablemodel.addRow(nowList_table_column);
                    nowUploadList_tablemodel.setValueAt(files.getFilename(), nowUpload_row, 0);
                    nowUploadList_tablemodel.setValueAt("0kb/s", nowUpload_row, 1);
                    nowUploadList_tablemodel.setValueAt(String.format("%.1f", Double.valueOf(files.getUpstatus())*1024 / (files.getFilesize()) * 100) + "%", nowUpload_row, 2);
                    nowUpload_row++;
                }
            }
        }

        alreadyUploadList_tablemodel = new DefaultTableModel(image_table_column, 0);
        alreadyUploadList_table = new JTable(alreadyUploadList_tablemodel);
        alreadyUploadList_table.setRowHeight(28);
        alreadyUploadList_table.getColumnModel().getColumn(0).setPreferredWidth(301);
        alreadyUploadList_table.getColumnModel().getColumn(0).setHeaderRenderer(tcr);
        alreadyUploadList_table.getColumnModel().getColumn(1).setPreferredWidth(119);
        alreadyUploadList_table.getColumnModel().getColumn(1).setHeaderRenderer(tcr);
        alreadyUploadList_table.getColumnModel().getColumn(2).setPreferredWidth(119);
        alreadyUploadList_table.getColumnModel().getColumn(2).setHeaderRenderer(tcr);
        alreadyUploadList_table.setShowGrid(false);

        leftUploadList = new ArrayList<>();
        leftUploadList.add(nowUploadList_table);
        leftUploadList.add(alreadyUploadList_table);

        //下载列表模块
        leftDownload_list = new JList();
        leftDownload_list.setBounds(0, 0, 95, 311);
        leftDownload_list.setBackground(new Color(240, 240, 240));
        leftDownload_list.setFont(new Font("等线", Font.BOLD, 15));
        leftDownload_list.setFixedCellHeight(30);
        leftDownload_list.setCellRenderer(renderer);
        leftDownload_list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                scrollPane.setViewportView(leftDownloadList.get(leftDownload_list.getSelectedIndex()));
                downloadButton.setEnabled(false);
            }
        });
        Set<String> functions3 = new LinkedHashSet<>();
        functions3.add("<html><font size=3>正在下载<p></html>");
        functions3.add("<html><font size=3>已下载<p></html>");
        leftDownload_list.setListData(functions3.toArray());

        nowDownloadList_tablemodel = new DefaultTableModel(nowList_table_column, 0);
        nowDownloadList_table = new JTable(nowDownloadList_tablemodel);
        nowDownloadList_table.setRowHeight(28);
        nowDownloadList_table.getColumnModel().getColumn(0).setPreferredWidth(301);
        nowDownloadList_table.getColumnModel().getColumn(0).setHeaderRenderer(tcr);
        nowDownloadList_table.getColumnModel().getColumn(1).setPreferredWidth(119);
        nowDownloadList_table.getColumnModel().getColumn(1).setHeaderRenderer(tcr);
        nowDownloadList_table.getColumnModel().getColumn(2).setPreferredWidth(119);
        nowDownloadList_table.getColumnModel().getColumn(2).setHeaderRenderer(tcr);
        nowDownloadList_table.setShowGrid(false);

        alreadyDownloadList_tablemodel = new DefaultTableModel(image_table_column, 0);
        alreadyDownloadList_table = new JTable(alreadyDownloadList_tablemodel);
        alreadyDownloadList_table.setRowHeight(28);
        alreadyDownloadList_table.getColumnModel().getColumn(0).setPreferredWidth(301);
        alreadyDownloadList_table.getColumnModel().getColumn(0).setHeaderRenderer(tcr);
        alreadyDownloadList_table.getColumnModel().getColumn(1).setPreferredWidth(119);
        alreadyDownloadList_table.getColumnModel().getColumn(1).setHeaderRenderer(tcr);
        alreadyDownloadList_table.getColumnModel().getColumn(2).setPreferredWidth(119);
        alreadyDownloadList_table.getColumnModel().getColumn(2).setHeaderRenderer(tcr);
        alreadyDownloadList_table.setShowGrid(false);

        leftDownloadList = new ArrayList<>();
        leftDownloadList.add(nowDownloadList_table);
        leftDownloadList.add(alreadyDownloadList_table);

        uploadButton = new JButton("上传");
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    int result = fileChooser.showOpenDialog(IndexFrame.this);
                    if (result == JFileChooser.CANCEL_OPTION) {
                        return;
                    }
                    java.io.File file = fileChooser.getSelectedFile();
                    if (file == null)
                        return;

                    long filesize = file.length();

                    Double size = Double.valueOf(spacesizeField.getText().substring(0, spacesizeField.getText().indexOf("/") - 1)) + Double.valueOf(filesize) / 1073741824;
                    if (size > 10.0) {
                        JOptionPane.showMessageDialog(IndexFrame.this, "空间不足！", "温馨提示", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String filename = file.getName();
                    String filetype = filename.substring(YunUtils.getLastPoint(filename) + 1);

                    File files = new File();
                    files.setFilename(filename);
                    files.setFilesize(filesize);
                    files.setFiletype(filetype);
                    files.setFilepath("E:\\Yun\\" + user.getUsername() + "\\" + filename);
                    files.setUsername(user.getUsername());
                    files.setLocalpath(file.getAbsolutePath());
                    //如果该目录下有该文件名的文件，提醒用户已上传过
                    if (filesSet.contains(files)) {
                        JOptionPane.showMessageDialog(IndexFrame.this, "该目录下已有此文件！", "温馨提示", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    Socket upSocket = new Socket("127.0.0.1", 6666);
                    ObjectOutputStream upOut = new ObjectOutputStream(upSocket.getOutputStream());
                    ObjectInputStream upIn = new ObjectInputStream(upSocket.getInputStream());

                    new UpLoadThread(file, files, upOut, upIn, 0).start();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        uploadButton.setBounds(1, 278, 93, 23);
        panel_1.add(uploadButton);

        downloadButton = new JButton("\u4E0B\u8F7D");
        downloadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
//                try {
//                    Socket downSocket=new Socket("127.0.0.1",8000);
//                    ObjectOutputStream downOut=new ObjectOutputStream(downSocket.getOutputStream());
//                    ObjectInputStream downIn=new ObjectInputStream(downSocket.getInputStream());
//
//                    String filename= yourChoiceInTree.toString();
//                    File files=new File();
//                    files.setFilename(filename);
//                    files.setUsername(user.getUsername());
//
//                    Message message=new Message();
//                    message.setType(MessageType.DOWNLOAD);
//                    message.setFiles(files);
//
//                    downOut.writeObject(message);
//                    downOut.flush();
//
//                    new DownloadThread(downOut,downIn).start();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

            }
        });
        downloadButton.setEnabled(false);
        downloadButton.setBounds(1, 250, 93, 23);
        panel_1.add(downloadButton);

        //右键继续上传
        continueUpload = new JMenuItem("继续上传");
        continueUpload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filename = (String) nowUploadList_table.getValueAt(choiceRow, 0);
                File files = null;
                for (File f : filesSet) {
                    if (f.getFilename().equals(filename)) {
                        files = f;
                        break;
                    }
                }
                try {
                    Socket uploadContinue = new Socket(BaseProperties.SERVER_IP, BaseProperties.SERVER_PORT);
                    ObjectOutputStream out = new ObjectOutputStream(uploadContinue.getOutputStream());
                    ObjectInputStream in = new ObjectInputStream(uploadContinue.getInputStream());
                    java.io.File file = new java.io.File(files.getLocalpath());

                    nowUploadList_tablemodel.removeRow(choiceRow);
                    nowUpload_row--;
                    new UpLoadThread(file, files, out, in, files.getUpstatus()).start();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        continueUpload.setSize(30, 20);
        pauseUpload = new JMenuItem("暂停");
        pauseUpload.setSize(30, 20);

        nowUploadList_RightKeyMenu = new JPopupMenu();
        nowUploadList_RightKeyMenu.add(continueUpload);
        nowUploadList_RightKeyMenu.add(pauseUpload);

        //右键删除文件
        deleteFile = new JMenuItem("删除");
        deleteFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                String filename=yourChoiceInTree.toString();
//                File files=null;
//                //从用户文件集合中找到该文件的信息并将它从文件队列集合中删除
//                for (File f:filesSet)
//                {
//                    if (f.getFilename().equals(filename))
//                    {
//                        files=f;
//                        filesSet.remove(f);
//                        break;
//                    }
//                }
//
//                Message message=new Message();
//                message.setFiles(files);
//                message.setType(MessageType.DELETEFILE);
//
//                try {
//                    out.writeObject(message);
//                    out.flush();
//                    out.reset();
//
//                    //删除后改变空间大小
//                    double nowspaceD=Double.valueOf(spacesizeField.getText().substring(0,spacesizeField.getText().indexOf("/")-1))-(double)files.getFilesize()/1073741824;
//                    String nowspace=String.format("%.2f",nowspaceD);
//                    spacesizeField.setText(nowspace+"/10.0G");
//                    //删除后将该文件从所有文件树中移除
//                    allFiles_model.removeNodeFromParent(yourChoiceInTree);
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }
            }
        });
        deleteFile.setSize(25, 15);

        allFileTree_RightKeyMenu = new JPopupMenu();
        allFileTree_RightKeyMenu.add(deleteFile);

        try {
            String style = "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel";
            UIManager.setLookAndFeel(style);
            String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
            UIManager.setLookAndFeel(lookAndFeel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 更新窗体样式
        SwingUtilities.updateComponentTreeUI(this);

//        new ReciveThread().start();
    }

    /**
     * 文件传输线程，上传一个文件开启一个线程
     */
    class UpLoadThread extends Thread {
        private File files;
        private java.io.File file;
        private ObjectOutputStream upOut;
        private ObjectInputStream upIn;
        long start;//开始读取文件的位置
        int this_row;//记录本文件所在行，用于在上传完毕后将速度归0和百分比拉满

        public UpLoadThread(java.io.File file, File files, ObjectOutputStream upOut, ObjectInputStream upIn, long start) {
            this.files = files;
            this.file = file;
            this.upOut = upOut;
            this.upIn = upIn;
            this.start = start;
        }

        public void run() {
            FileInputStream fin = null;
            try {
                nowUploadList_tablemodel.addRow(nowList_table_column);
                this_row = nowUpload_row;
                nowUploadList_tablemodel.setValueAt(files.getFilename(), this_row, 0);
                nowUploadList_tablemodel.setValueAt(0 + "k/s", this_row, 1);
                nowUploadList_tablemodel.setValueAt(0 + "%", nowUpload_row, 2);
                nowUpload_row++;

                files.setMd5(YunUtils.getMD5(file.getAbsolutePath()));
                Request request = new Request();
                request.setMethodValue(MethodValue.UPLOAD_EXIST_FILE);
                Map<String, Object> params = new HashMap();
                params.put("file", files);
                request.setParams(params);
                out.writeObject(request);
                out.flush();
                out.reset();

                Response response = (Response) in.readObject();
                if (ResponseMessage.EXIST_INTACT_FILE.equals(response.getResponseMessage())) {
                    File file = (File) response.getParams().get("file");
                    filesSet.add(file);
                } else {
                    request.setMethodValue(MethodValue.UPLOAD_FILE);
                    upOut.writeObject(request);
                    upOut.flush();
                    fin = new FileInputStream(file);
                    if (start > 0) {
                        fin.skip(start * 1024);
                    }
                    int transUnitLength = 1024;
                    long transcount = files.getFilesize() / transUnitLength;

                    double nowTime = Calendar.getInstance().getTimeInMillis();
                    double endTime = 0;
                    long alreadylen = 0;
                    for (long n = start; n < transcount; n++) {
                        byte[] bs = new byte[transUnitLength];
                        int length = fin.read(bs);
                        alreadylen += length;
                        upOut.write(bs, 0, length);
                        upOut.flush();
                        if (n % 1000 == 0) {
                            endTime = Calendar.getInstance().getTimeInMillis();
                            double runTime = endTime - nowTime;
                            double v = (alreadylen * 0.977) / runTime;
                            if (v > 1024) {
                                nowUploadList_tablemodel.setValueAt(String.format("%.1f", v / 1024) + "M/s", this_row, 1);
                                continue;
                            }
                            nowUploadList_tablemodel.setValueAt(String.format("%.1f", v) + "k/s", this_row, 1);
                            alreadylen = 0;
                            nowTime = Calendar.getInstance().getTimeInMillis();
                        }
                        nowUploadList_tablemodel.setValueAt(String.format("%.1f", Double.valueOf(n) / transcount * 100) + "%", this_row, 2);
                    }
                    upOut.close();
                    upIn.close();
                }
                nowUploadList_tablemodel.setValueAt(0 + "k/s", this_row, 1);
                nowUploadList_tablemodel.setValueAt(100 + "%", this_row, 2);

                //将已上传文件插入已上传列表的表格里
                alreadyUploadList_tablemodel.addRow(image_table_column);
                alreadyUploadList_tablemodel.setValueAt(files.getFilename(), alreadyUpload_row, 0);
                alreadyUploadList_tablemodel.setValueAt(files.getFiletype(), alreadyUpload_row, 1);
                String filesize = null;
                if (files.getFilesize() / 1024 < 1024) {
                    filesize = files.getFilesize() / 1024 + "K";
                } else {
                    filesize = String.format("%.1f", files.getFilesize() / 1048576.0) + "M";
                }
                alreadyUploadList_tablemodel.setValueAt(filesize, alreadyUpload_row, 2);
                alreadyUpload_row++;
                allFiles_model.insertNodeInto(new DefaultMutableTreeNode(files.getFilename()), mySource, mySource.getChildCount());
                filesSet.add(files);
                //文件上传后更新存储空间大小
                double nowspaceD = Double.valueOf(spacesizeField.getText().substring(0, spacesizeField.getText().indexOf("/") - 1)) + (double) files.getFilesize() / 1073741824;
                String nowspace = String.format("%.2f", nowspaceD);
                spacesizeField.setText(nowspace + "/10.0G");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                nowUploadList_tablemodel.setValueAt(0 + "k/s", this_row, 1);
                nowUploadList_tablemodel.setValueAt(100 + "%", this_row, 2);
                //将已上传文件插入已上传列表的表格里
                alreadyUploadList_tablemodel.addRow(image_table_column);
                alreadyUploadList_tablemodel.setValueAt(files.getFilename(), alreadyUpload_row, 0);
                alreadyUploadList_tablemodel.setValueAt(files.getFiletype(), alreadyUpload_row, 1);
                String filesize = null;
                if (files.getFilesize() / 1024 < 1024) {
                    filesize = files.getFilesize() / 1024 + "K";
                } else {
                    filesize = String.format("%.1f", files.getFilesize() / 1048576.0) + "M";
                }
                alreadyUploadList_tablemodel.setValueAt(filesize, alreadyUpload_row, 2);
                alreadyUpload_row++;
                allFiles_model.insertNodeInto(new DefaultMutableTreeNode(files.getFilename()), mySource, mySource.getChildCount());
                filesSet.add(files);
                //文件上传后更新存储空间大小
                double nowspaceD = Double.valueOf(spacesizeField.getText().substring(0, spacesizeField.getText().indexOf("/") - 1)) + (double) files.getFilesize() / 1073741824;
                String nowspace = String.format("%.2f", nowspaceD);
                spacesizeField.setText(nowspace + "/10.0G");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 文件下载线程，下一个文件开启一个线程
     */
    class DownloadThread extends Thread {
        ObjectOutputStream downOut;
        ObjectInputStream downIn;

        public DownloadThread(ObjectOutputStream downOut, ObjectInputStream downIn) {
            this.downIn = downIn;
            this.downOut = downOut;
        }

        @Override
        public void run() {
//            try {
//                Message replay =(Message) downIn.readObject();
//                Files files=replay.getFiles();
//                FileOutputStream fout=new FileOutputStream("D:\\YunDiskDownload\\"+files.getFilename());
//
//                int this_row=nowDownload_row;//本文件所在行
//                nowDownload_row++;
//                nowDownloadList_tablemodel.addRow(nowList_table_column);
//                nowDownloadList_tablemodel.setValueAt(files.getFilename(),this_row,0);
//                nowDownloadList_tablemodel.setValueAt(0+"kb/s",this_row,1);
//                nowDownloadList_tablemodel.setValueAt(0+"%",this_row,2);
//
//                long filesize=files.getFilesize();
//                int transUnitLength=1024;
//                long transcount=filesize/transUnitLength;
//
//                double nowTime=Calendar.getInstance().getTimeInMillis();
//                double endTime=0;
//                long alreadylen=0;
//                for (long n=0;n<transcount;n++)
//                {
//                    byte[] bs=new byte[transUnitLength];
//                    int length=downIn.read(bs);
//                    alreadylen=alreadylen+length;
//                    fout.write(bs,0,length);
//                    fout.flush();
//                    if (n%1000==0)
//                    {
//                        endTime= Calendar.getInstance().getTimeInMillis();
//                        double runTime=endTime-nowTime;
//                        double v=(alreadylen*0.977)/runTime;
//                        if (v>1024)
//                        {
//                            nowDownloadList_tablemodel.setValueAt(String.format("%.1f",v/1024)+"M/s",this_row,1);
//                            continue;
//                        }
//                        nowDownloadList_tablemodel.setValueAt(String.format("%.1f",v)+"k/s",this_row,1);
//                    }
//                    nowDownloadList_tablemodel.setValueAt(String.format("%.1f",Double.valueOf(n)/transcount*100)+"%",this_row,2);
//                }
//                nowDownloadList_tablemodel.setValueAt(0+"k/s",this_row,1);
//                nowDownloadList_tablemodel.setValueAt(100+"%",this_row,2);
//                downIn.close();
//                downOut.close();
//                fout.close();
//
//                alreadyDownloadList_tablemodel.addRow(image_table_column);
//                alreadyDownloadList_tablemodel.setValueAt(files.getFilename(),alreadyDownload_row,0);
//                alreadyDownloadList_tablemodel.setValueAt(files.getFiletype(),alreadyDownload_row,1);
//                String fileLength=null;
//                if (files.getFilesize()/1024<1024)
//                {
//                    fileLength=files.getFilesize()/1024+"K";
//                }
//                else
//                {
//                    fileLength=String.format("%.1f",files.getFilesize()/1048576.0)+"M";
//                }
//                alreadyDownloadList_tablemodel.setValueAt(fileLength,alreadyDownload_row,2);
//                alreadyDownload_row++;
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//
        }
    }


    /**
     * 当点击左边的文件类型JList时，对于不同类型的文件在不同的表格中加入相应数量的行数
     *
     * @param tableModel 要插入文件的表格
     * @param type       文件类型
     */
    private void addRowIntoTable(DefaultTableModel tableModel, String... type) {
        Set<File> thisFiles = new HashSet<>();
        for (File files : filesSet) {
            for (int i = 0; i < type.length; i++) {
                if (files.getFiletype().equals(type[i])) {
                    thisFiles.add(files);
                    break;
                }
            }
        }
        if (tableModel.getRowCount() != thisFiles.size()) {
            for (File files : thisFiles) {
                tableModel.addRow(image_table_column);
                tableModel.setValueAt(files.getFilename(), tableModel.getRowCount() - 1, 0);
                tableModel.setValueAt(files.getFiletype(), tableModel.getRowCount() - 1, 1);
                String filesize = null;
                if (files.getFilesize() / 1024 < 1024) {
                    filesize = files.getFilesize() / 1024 + "K";
                } else {
                    filesize = String.format("%.1f", files.getFilesize() / 1048576.0) + "M";
                }
                tableModel.setValueAt(filesize, tableModel.getRowCount() - 1, 2);
            }
        }
    }
}