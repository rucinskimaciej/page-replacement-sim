package sop.pageReplacement.common;

import javax.swing.*;

public class FramesCleaner {

    private static String clean(String frames) {
        if (frames == null) return null;
        frames = frames.replace(")(", " ");
        return frames.substring(1, frames.length() - 1);
    }



    public static void main(String[] args) {
        String input;
        do {
            input = JOptionPane.showInputDialog("Enter frames:");
            System.out.println(input);
            String output = clean(input);
            System.out.println(output);
        } while (input != null);
        JOptionPane.showMessageDialog(null,"You closed the converter");
    }
}
