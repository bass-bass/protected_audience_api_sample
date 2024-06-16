package com.example.dsp.common.conf;

import java.util.Random;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.example.dsp.api.action.serving.form.ServingForm;
import com.example.dsp.api.action.serving.logic.InterestGroupManager;
import com.example.dsp.common.io.CustomCipher;

public class InterestGroupTest {
	@Test
	public void testInterestGroup() throws Exception {
		ServingForm form = generateForm(100);
		InterestGroupManager.IgInfo info = generateIgInfo(100);
		InterestGroup ig = InterestGroupManager.createInterestGroup(form, info);

		System.out.println(ig.toJson());
	}

	public ServingForm generateForm(int seed) {
		Random r = new Random(seed);
		ServingForm form = Mockito.mock(ServingForm.class);
		CustomCipher cipher = new CustomCipher("mykey", "Blowfish");
		Mockito.when(form.getCipher()).thenReturn(cipher);
		Mockito.when(form.getIgId()).thenReturn(r.nextInt(1000));
		Mockito.when(form.getScheme()).thenReturn("https");
		Mockito.when(form.getLpUrl()).thenReturn("https://example.com/lp");
		Mockito.when(form.getCreativeUrl()).thenReturn("https://example.com/creative");
		return form;
	}

	public InterestGroupManager.IgInfo generateIgInfo(int seed) {
		Random r = new Random(seed);
		return new InterestGroupManager.IgInfo(r.nextInt(1000), new int[] {r.nextInt(1000), r.nextInt(1000)}, seed);
	}

	@Test
	public void testCipher() throws Exception {
		CustomCipher cipher = new CustomCipher("mykey", "Blowfish");
		try {
			String en = cipher.encrypt(""+645);
			System.out.println(en + " : " + en.getBytes().length);
			String de = cipher.decrypt(en);
			System.out.println(de + " : " + de.getBytes().length);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
