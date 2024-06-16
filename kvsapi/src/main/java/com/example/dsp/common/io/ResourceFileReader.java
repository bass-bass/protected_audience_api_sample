package com.example.dsp.common.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.URI;
import java.net.URISyntaxException;

public class ResourceFileReader {
	private ResourceFileReader() {
	}

	public static BufferedReader newBufferedReader(String fileName) {
		return new BufferedReader(new InputStreamReader(newInputStream(fileName)));
	}

	public static InputStream newInputStream(String fileName) {
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
	}

	public static FileInputStream newFileInputStream(String fileName) throws IOException, URISyntaxException {
		URI uri = ResourceFileReader.class.getClassLoader().getResource("").toURI();
		File file = new File(new URI(uri + fileName));;
		return new FileInputStream(file);
	}

	public static ObjectInputStream newObjectInputStream(String fileName) throws IOException {
		InputStream is = newInputStream(fileName);
		return new ObjectInputStream(is);
	}
}
