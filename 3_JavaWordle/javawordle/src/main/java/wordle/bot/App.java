package wordle.bot;

import wordle.bot.gui.Selector;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        new Selector().setVisible(true);
    }
}
