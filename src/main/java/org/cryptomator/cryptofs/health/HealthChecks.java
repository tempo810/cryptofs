package org.cryptomator.cryptofs.health;

import org.cryptomator.cryptofs.VaultConfig;
import org.cryptomator.cryptofs.common.Constants;
import org.cryptomator.cryptofs.health.api.HealthCheck;
import org.cryptomator.cryptolib.api.MasterkeyLoader;
import org.cryptomator.cryptolib.api.MasterkeyLoadingFailedException;
import org.cryptomator.cryptolib.api.UnsupportedVaultFormatException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class HealthChecks {

	private static final Collection<HealthCheck> ALL_CHECKS = List.of();

	public static void run(Path pathToVault, String vaultConfigFileName, MasterkeyLoader masterkeyLoader, Collection<HealthCheck> checksToRun) throws IOException, UnsupportedVaultFormatException, MasterkeyLoadingFailedException {
		var vaultConfigPath = pathToVault.resolve(vaultConfigFileName);
		var token = Files.readString(vaultConfigPath, StandardCharsets.US_ASCII);
		var unverifiedCfg = VaultConfig.decode(token);
		if (unverifiedCfg.allegedVaultVersion() != Constants.VAULT_VERSION) {
			throw new UnsupportedVaultFormatException(unverifiedCfg.allegedVaultVersion(), Constants.VAULT_VERSION);
		}
		byte[] rawKey = new byte[0];
		try (var masterkey = masterkeyLoader.loadKey(unverifiedCfg.getKeyId())) {
			rawKey = masterkey.getEncoded();
			var cfg = unverifiedCfg.verify(rawKey, Constants.VAULT_VERSION);
			checksToRun.forEach(check -> check.check(pathToVault, cfg, masterkey)); // TODO collect result
		} finally {
			Arrays.fill(rawKey, (byte) 0x00);
		}
	}

}
