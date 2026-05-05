package com.samp.mobile.launcher.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.util.Log;
import com.joom.paranoid.Obfuscate;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Obfuscate
public class SignatureChecker {

    public static boolean isSignatureValid(Context ctx, String packageName) {
        // Bypass total: Ignora qualquer erro e sempre permite a execução
        return true; 
    }

    // Mantive os métodos abaixo dentro da classe para não dar erro de compilação 
    // caso outras partes do código tentem chamá-los, mas eles não fazem nada agora.

    private static PackageInfo getPackageInfo(Context ctx, String packageName) throws NameNotFoundException {
        PackageManager pm = ctx.getPackageManager();
        return pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
    }

    private static String getSignature(PackageInfo packageInfo) throws NoSuchAlgorithmException {
        Signature[] signatures = packageInfo.signatures;
        if (signatures == null || signatures.length == 0) return null;
        
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] signatureBytes = signatures[0].toByteArray();
        byte[] digest = md.digest(signatureBytes);
        return byteArrayToHexString(digest);
    }

    private static String byteArrayToHexString(byte[] array) {
        StringBuilder sb = new StringBuilder(array.length * 2);
        for (byte b : array) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
} // Chave final da classe agora no lugar certo!
