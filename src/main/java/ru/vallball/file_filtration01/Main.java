package ru.vallball.file_filtration01;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) throws IOException {
		List<String> namesOfFiles = new ArrayList<>();
		String path = "";
		String prefix = "";
		boolean appendable = false;
		boolean fullStatistics = false;
		boolean shortStatistics = false;
		int i = 0;
		while (i != args.length) {
			if (args[i].equals("-p")) {
				if (i + 1 == args.length) {
					System.out.println("You forgot to write the prefix");
					i++;
				} else {
					prefix = args[i + 1];
					i += 2;
				}
			} else if (args[i].equals("-o")) {
				if (i + 1 == args.length) {
					System.out.println("You forgot to write the path");
					i++;
				} else {
					path = args[i + 1];
					i += 2;
				}
			} else if (args[i].equals("-a")) {
				appendable = true;
				i++;
			} else if (args[i].equals("-f")) {
				fullStatistics = true;
				i++;
			} else if (args[i].equals("-s")) {
				shortStatistics = true;
				i++;
			} else {
				namesOfFiles.add(args[i]);
				i++;
			}
		}
		
		List<String> files = FileHandler.readFiles(namesOfFiles);
		for (String s : files) {
			System.out.println(s);
		}
		System.out.println("------------------------");
		List<String> content = FileHandler.contentProcessing(files);
		for (String s : content) {
			System.out.println(s);
		}
		FileHandler.writeFiles(content, path, prefix, appendable, fullStatistics, shortStatistics);
	}
}
