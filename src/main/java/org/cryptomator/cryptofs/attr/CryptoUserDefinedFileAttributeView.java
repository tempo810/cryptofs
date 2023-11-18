package org.cryptomator.cryptofs.attr;

import org.cryptomator.cryptofs.CryptoPath;
import org.cryptomator.cryptofs.CryptoPathMapper;
import org.cryptomator.cryptofs.Symlinks;
import org.cryptomator.cryptofs.fh.OpenCryptoFiles;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.LinkOption;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.List;

@AttributeViewScoped
final class CryptoUserDefinedFileAttributeView extends AbstractCryptoFileAttributeView implements UserDefinedFileAttributeView {

	@Inject
	public CryptoUserDefinedFileAttributeView(CryptoPath cleartextPath, CryptoPathMapper pathMapper, LinkOption[] linkOptions, Symlinks symlinks, OpenCryptoFiles openCryptoFiles) {
		super(cleartextPath, pathMapper, linkOptions, symlinks, openCryptoFiles);
	}

	@Override
	public String name() {
		return "user";
	}

	@Override
	public List<String> list() throws IOException {
		return getCiphertextAttributeView(UserDefinedFileAttributeView.class).list();
	}

	@Override
	public int size(String name) throws IOException {
		return getCiphertextAttributeView(UserDefinedFileAttributeView.class).size(name);
	}

	@Override
	public int read(String name, ByteBuffer dst) throws IOException {
		UserDefinedFileAttributeView fileAttributeView = getCiphertextAttributeView(UserDefinedFileAttributeView.class);
		return fileAttributeView.read(name, dst);
	}

	@Override
	public int write(String name, ByteBuffer src) throws IOException {
		UserDefinedFileAttributeView fileAttributeView = getCiphertextAttributeView(UserDefinedFileAttributeView.class);
		return fileAttributeView.write(name, src);
	}

	@Override
	public void delete(String name) throws IOException {
		UserDefinedFileAttributeView fileAttributeView = getCiphertextAttributeView(UserDefinedFileAttributeView.class);
		fileAttributeView.delete(name);
	}
}
