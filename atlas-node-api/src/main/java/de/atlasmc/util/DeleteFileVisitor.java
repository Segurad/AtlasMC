package de.atlasmc.util;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class DeleteFileVisitor extends SimpleFileVisitor<Path> {
	
	public static final FileVisitor<Path> INSTANCE = new DeleteFileVisitor();
	
	private DeleteFileVisitor() {}
	
	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		file.toFile().delete();
		return FileVisitResult.CONTINUE;
	}
	
	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
		dir.toFile().delete();
		return FileVisitResult.CONTINUE;
	}

}
