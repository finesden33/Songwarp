package view;

import interface_adapter.GetPlaylistState;
import interface_adapter.GetPlaylistViewModel;

import interface_adapter.load_playlist.LoadPlaylistController;
import interface_adapter.load_token.LoadTokenController;
import interface_adapter.save_token.SaveTokenController;
import interface_adapter.spotify_get.SpotifyGetController;
import interface_adapter.youtube_get.YoutubeGetController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class InitialView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "page 1";
    private final GetPlaylistViewModel getPlaylistViewModel;
    private final JTextField urlInputField = new JTextField(40);
    private final YoutubeGetController youtubeGetController;
    private final SpotifyGetController spotifyGetController;
    private final LoadPlaylistController loadPlaylistController;
    private final LoadTokenController loadTokenController;
    private final SaveTokenController saveTokenController;
    private final JButton youtubeGet;
    private final JButton spotifyGet;
    private final JButton loadPlaylist;
    private final JButton loadToken;
    private final JButton saveToken;

    public InitialView(GetPlaylistViewModel getPlaylistViewModel,
                       YoutubeGetController youtubeGetController,
                       SpotifyGetController spotifyGetController,
                       LoadPlaylistController loadPlaylistController,
                       LoadTokenController loadTokenController,
                       SaveTokenController saveTokenController
                       ) {
        this.getPlaylistViewModel = getPlaylistViewModel;
        this.youtubeGetController = youtubeGetController;
        this.spotifyGetController = spotifyGetController;
        this.loadPlaylistController = loadPlaylistController;
        this.loadTokenController = loadTokenController;
        this.saveTokenController = saveTokenController;

        getPlaylistViewModel.addPropertyChangeListener(this);

        JLabel title = new JLabel(getPlaylistViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        LabelTextPanel urlInput = new LabelTextPanel(
                new JLabel(getPlaylistViewModel.URL_LABEL), urlInputField);

        JPanel buttons = new JPanel();
        youtubeGet = new JButton(getPlaylistViewModel.YOUTUBEGET_BUTTON_LABEL);
        buttons.add(youtubeGet);
        spotifyGet = new JButton(getPlaylistViewModel.SPOTIFYGET_BUTTON_LABEL);
        buttons.add(spotifyGet);
        loadPlaylist = new JButton(getPlaylistViewModel.LOADPLAYLIST_BUTTON_LABEL);
        buttons.add(loadPlaylist);

        JPanel keyStuff = new JPanel();
        loadToken = new JButton(getPlaylistViewModel.LOADTOKEN_BUTTON_LABEL);
        keyStuff.add(loadToken);
        saveToken = new JButton(getPlaylistViewModel.TOKENSAVE_BUTTON_LABEL);
        keyStuff.add(saveToken);


        loadToken.addActionListener(
                e -> {
                    if (e.getSource().equals(loadToken)) {
                        loadTokenController.execute();
                    }
                }
        );
        saveToken.addActionListener(
                e -> {
                    if (e.getSource().equals(saveToken)) {
                        JTextField[] textFields = new JTextField[5];
                        JPanel panel = new JPanel(new GridLayout(0, 1));
                        String[] keys = {"youtubeKey", "youtubeClientID", "youtubeClientSecret", "spotifyClientID", "spotifyClientSecret"};

                        for (int i = 0; i < 5; i++) {
                            textFields[i] = new JTextField(30); // You can adjust the size of the text fields here
                            panel.add(new JLabel(keys[i]));
                            panel.add(textFields[i]);
                        }
                        int result = JOptionPane.showConfirmDialog(null, panel,
                                "Enter the appropriate information", JOptionPane.OK_CANCEL_OPTION);
                        if (result == JOptionPane.OK_OPTION) {
                            StringBuilder info = new StringBuilder();
                            for (int i = 0; i < 5; i++) {
                                info.append(keys[i]).append(": ").append(textFields[i].getText()).append("\n");
                            }
                            System.out.println("Entered information:\n" + info.toString());

                            saveTokenController.execute(textFields);
                        }

                    }
                }
        );

        loadPlaylist.addActionListener(
                e -> {
                    if (e.getSource().equals(loadPlaylist)) {
                        loadPlaylistController.execute();
                    }
                }
        );
        youtubeGet.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    GetPlaylistState currentState = getPlaylistViewModel.getState();
                    if (e.getSource().equals(youtubeGet)) {
                        youtubeGetController.execute(currentState.getUrlInput());
                    }
                }
            }
        );
        spotifyGet.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        GetPlaylistState currentState = getPlaylistViewModel.getState();
                        if (e.getSource().equals(spotifyGet)) {
                            spotifyGetController.execute(currentState.getUrlInput());
                        }
                    }
                }
        );

        urlInputField.addKeyListener(
            new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                    GetPlaylistState currentState = getPlaylistViewModel.getState();
                    String text = urlInputField.getText() + e.getKeyChar();
                    currentState.setUrlInput(text);
                    getPlaylistViewModel.setState(currentState);
                }

                @Override
                public void keyPressed(KeyEvent e) {
                }

                @Override
                public void keyReleased(KeyEvent e) {
                }
            });
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(urlInput);
        this.add(buttons);
        this.add(keyStuff);
    }

    /**
     * React to a button click that results in evt.
     */
    public void actionPerformed(ActionEvent evt) {
        JOptionPane.showConfirmDialog(this, "not implemented yet.");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Object newValue = evt.getNewValue();
        System.out.println("Property change caught: " + newValue.toString());
        if (newValue instanceof GetPlaylistState state) {
            if (state.getError() != null) {
                JOptionPane.showMessageDialog(this, state.getError());
            }
        }
    }
}


