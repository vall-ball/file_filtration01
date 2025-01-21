package ru.vallball.file_filtration01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

	public static List<String> readFiles(List<String> namesOfFiles) {
		List<String> answer = new ArrayList<>();
		for (String name : namesOfFiles) {
			try {
				answer.add(Files.readString(Paths.get(name)));
			} catch (IOException e) {
				System.out.println("Such a file " + name + " doesn't exist!");
			}
		}
		return answer;
	}

	public static List<String> contentProcessing(List<String> contentOfFiles) {
		List<String> answer = new ArrayList<>();
		List<String[]> list = new ArrayList<>();
		int count = 0;
		for (String s : contentOfFiles) {
			list.add(s.split(System.lineSeparator()));
		}
		int index = 0;
		while (true) {
			count = 0;
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).length > index) {
					answer.add(list.get(i)[index]);
				} else {
					count++;
				}
			}
			index++;
			if (count == list.size()) {
				break;
			}
		}
		return answer;
	}

	public static void writeFiles(List<String> contentOfFiles, String path, String prefix, boolean appendable,
			boolean fullStatistics, boolean shortStatistics) {
		int quantityOfIntegers = 0;
		int quantityOfFloats = 0;
		int quantityOfStrings = 0;
		long minInt = Long.MAX_VALUE;
		long maxInt = Long.MIN_VALUE;
		long sumOfIntegers = 0;
		float minFloat = Float.MAX_VALUE;
		float maxFloat = Float.MIN_VALUE;
		float sumOfFloats = 0;
		int minLength = Integer.MAX_VALUE;
		int maxLength = 0;
		String currentPath = "";
		try {
			currentPath = new java.io.File(".").getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (path.length() == 0) {
			path = currentPath;
		}
		if (!appendable) {
			if (Files.exists(Paths.get(path + "\\" + prefix + "integers.txt"))) {
				try {
					Files.delete(Paths.get(path + "\\" + prefix + "integers.txt"));
				} catch (IOException e) {
					System.out.println("Can't delete the file " + path + "\\" + prefix + "integers.txt");
					e.printStackTrace();
				}
			}
			if (Files.exists(Paths.get(path + "\\" + prefix + "floats.txt"))) {
				try {
					Files.delete(Paths.get(path + "\\" + prefix + "floats.txt"));
				} catch (IOException e) {
					System.out.println("Can't delete the file " + path + "\\" + prefix + "floats.txt");
					e.printStackTrace();
				}
			}
			if (Files.exists(Paths.get(path + "\\" + prefix + "strings.txt"))) {
				try {
					Files.delete(Paths.get(path + "\\" + prefix + "strings.txt"));
				} catch (IOException e) {
					System.out.println("Can't delete the file " + path + "\\" + prefix + "strings.txt");
					e.printStackTrace();
				}
			}
		}
		Path integers = null;
		Path floats = null;
		Path strings = null;
		for (String s : contentOfFiles) {
			if (s.matches("0|-?[1-9][0-9]*")) {
				if (!Files.exists(Paths.get(path + "\\" + prefix + "integers.txt"))) {
					try {
						integers = Files.createFile(Paths.get(path + "\\" + prefix + "integers.txt"));
					} catch (IOException e) {
						System.out.println("Can't create the file " + path + "\\" + prefix + "integers.txt");
						e.printStackTrace();
					}
				} else {
					integers = Paths.get(path + "\\" + prefix + "integers.txt");
				}
				try {
					Files.writeString(integers, s + System.lineSeparator(), StandardOpenOption.APPEND);
				} catch (IOException e) {
					e.printStackTrace();
				}
				quantityOfIntegers++;
				long a = 0;
				try {
					a = Long.parseLong(s);
				} catch (NumberFormatException e) {
					System.out.println("This number is too big " + s);
				}
				if (a < minInt) {
					minInt = a;
				}
				if (a > maxInt) {
					maxInt = a;
				}
				sumOfIntegers += a;
			} else if (s.matches("-?(0|[1-9][0-9]*)\\.[0-9]+((E|e)(\\+|-)[1-9][0-9]*)?")) {
				if (!Files.exists(Paths.get(path + "\\" + prefix + "floats.txt"))) {
					try {
						floats = Files.createFile(Paths.get(path + "\\" + prefix + "floats.txt"));
					} catch (IOException e) {
						System.out.println("Can't create the file " + path + "\\" + prefix + "floats.txt");
						e.printStackTrace();
					}
				} else {
					floats = Paths.get(path + "\\" + prefix + "floats.txt");
				}
				try {
					Files.writeString(floats, s + System.lineSeparator(), StandardOpenOption.APPEND);
				} catch (IOException e) {
					e.printStackTrace();
				}
				quantityOfFloats++;
				float a = 0;
				try {
					a = Float.parseFloat(s);
				} catch (NumberFormatException e) {
					System.out.println("This number is too big " + s);
				}
				if (a < minFloat) {
					minFloat = a;
				}
				if (a > maxFloat) {
					maxFloat = a;
				}
				sumOfFloats += a;
			} else {
				if (!Files.exists(Paths.get(path + "\\" + prefix + "strings.txt"))) {
					try {
						strings = Files.createFile(Paths.get(path + "\\" + prefix + "strings.txt"));
					} catch (IOException e) {
						System.out.println("Can't create the file " + path + "\\" + prefix + "strings.txt");
						e.printStackTrace();
					}
				} else {
					strings = Paths.get(path + "\\" + prefix + "strings.txt");
				}
				try {
					Files.writeString(strings, s + System.lineSeparator(), StandardOpenOption.APPEND);
				} catch (IOException e) {
					e.printStackTrace();
				}
				quantityOfStrings++;
				if (s.length() < minLength) {
					minLength = s.length();
				}
				if (s.length() > maxLength) {
					maxLength = s.length();
				}
			}
		}
		if (fullStatistics) {
			if (quantityOfIntegers != 0) {
				System.out.println("There were " + quantityOfIntegers + " integers written down. Maximum = " + maxInt
						+ ". Minimum = " + minInt + ". Sum = " + sumOfIntegers + ". Average = "
						+ (0.0 + sumOfIntegers / quantityOfIntegers) + ".");
			}
			if (quantityOfFloats != 0) {
				System.out.println("There were " + quantityOfFloats + " floats written down. Maximum = " + maxFloat
						+ ". Minimum = " + minFloat + ". Sum = " + sumOfFloats + ". Average = "
						+ (sumOfFloats / quantityOfFloats) + ".");
			}
			if (quantityOfStrings != 0) {
				System.out.println("There were " + quantityOfStrings + " strings written down. Maximum length= "
						+ maxLength + ". Minimum length = " + minLength + ".");
			}
		} else if (shortStatistics) {
			if (quantityOfIntegers != 0) {
				System.out.println("There were " + quantityOfIntegers + " integers written down.");
			}
			if (quantityOfFloats != 0) {
				System.out.println("There were " + quantityOfFloats + " floats written down.");
			}
			if (quantityOfStrings != 0) {
				System.out.println("There were " + quantityOfStrings + " strings written down.");
			}
		}
	}
}
