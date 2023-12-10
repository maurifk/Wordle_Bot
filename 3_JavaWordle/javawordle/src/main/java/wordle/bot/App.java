package wordle.bot;

import wordle.bot.gui.Selector;

public final class App {
    private App() {
    }

    public static void main(String[] args) {
        new Selector().setVisible(true);
    }
}
