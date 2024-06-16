package com.example.dsp.api.action.serving.logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.example.dsp.api.action.serving.form.ServingForm;
import com.example.dsp.common.conf.InterestGroup;
import com.example.dsp.common.io.ResourceFileReader;
import com.example.dsp.common.logger.Logger;

@Component
public class ServingLogic {
	private static final Logger logger = Logger.getLogger(ServingLogic.class.getSimpleName());
	private static final String LF = "\n";

	public ResponseEntity<String> makeIndexTag(ServingForm form) throws IOException {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Ad-Auction-Allowed", "true");
		responseHeaders.set("SUPPORTS-LOADING-MODE", "fenced-frame");
		responseHeaders.set("Content-Type", "text/javascript; charset=UTF-8");
		String tag = readFile("join-ad-interest-group.js");

		return new ResponseEntity<String>(tag, responseHeaders, HttpStatus.OK);
	}

	public ResponseEntity<String> makeJoinTag(ServingForm form, List<InterestGroup> igs4Join, List<InterestGroup> igs4Leave) {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Ad-Auction-Allowed", "true");
		responseHeaders.set("SUPPORTS-LOADING-MODE", "fenced-frame");
		responseHeaders.set("Content-Type", "text/html; charset=UTF-8");
		String tag = addScript4JoinAdInterestGroups(igs4Join, igs4Leave);

		return new ResponseEntity<String>(tag.toString(), responseHeaders, HttpStatus.OK);
	}

	public ResponseEntity<String> makeCreativeTag(ServingForm form) throws IOException {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Ad-Auction-Allowed", "true");
		responseHeaders.set("SUPPORTS-LOADING-MODE", "fenced-frame");
		responseHeaders.set("Content-Type", "text/html; charset=UTF-8");
		StringBuilder tag = new StringBuilder();

		tag.append(addHtmlHeader());
		tag.append(addImageBody(form.getLpUrl(), form.getCreativeUrl(), form.getTarget()));
		tag.append(addScript4Creative());
		tag.append(addHtmlFooter());

		return new ResponseEntity<String>(tag.toString(), responseHeaders, HttpStatus.OK);
	}
	public ResponseEntity<String> makeLogicTag(ServingForm form) throws IOException {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Ad-Auction-Allowed", "true");
		responseHeaders.set("SUPPORTS-LOADING-MODE", "fenced-frame");
		responseHeaders.set("Content-Type", "text/javascript; charset=UTF-8");
		String tag = addScript4Logic(form);

		return new ResponseEntity<String>(tag, responseHeaders, HttpStatus.OK);
	}

	private static String addHtmlHeader() {
		return "<html lang=\"ja\"><body>";
	}

	private static String addScript4JoinAdInterestGroups(List<InterestGroup> igs4Join, List<InterestGroup> igs4Leave) {
		StringBuilder script = new StringBuilder("<script>" + LF);
		script.append("const igNames = [];").append(LF);
		script.append("const promises = [];").append(LF);
		int i = 0;
		for (InterestGroup ig : igs4Join) {
			script.append("const interestGroup").append(i).append(" = ").append(ig.toJson()).append(";").append(LF)
					.append("const joinPromise").append(i).append(" = navigator.joinAdInterestGroup(interestGroup").append(i).append(")")
					.append(".then((result) => { igNames.push(interestGroup").append(i).append(".name);});").append(LF)
					.append("promises.push(joinPromise").append(i).append(");").append(LF);
			i++;
		}
		for (InterestGroup ig : igs4Leave) {
			script.append("navigator.leaveAdInterestGroup({ \"owner\": \"").append(ig.getOwner()).append("\", \"name\": \"").append(ig.getName()).append("\"});").append(LF);
		}
		script.append("Promise.all(promises).then(() => { console.log(\"Joined Ig Names: \", igNames); });").append(LF);
		script.append("</script>");
		return script.toString();
	}
	private static String addScript4Creative() throws IOException {
		return "<script>" + readFile("creative.js") + "</script>";
	}
	private static String addScript4Logic(ServingForm form) throws IOException {
		return readFile("bidding_logic.js");
	}

	// Image HTML Tag
	public static String addImageBody(String clickAcqURL, String viewAcqURL, String target) {
		return "<a onclick=\"sendClickEvent()\" href=\"" + clickAcqURL + "\" target=\"" + target + "\">" + "<img "
				+ "src=\"" + viewAcqURL + "\" "
				+ "width=\""
				+ 320 + "\" height=\"" + 50 + " border=\"0\"/>" + "</a>";
	}

	private static String addHtmlFooter() {
		return "</body></html>";
	}

	private static String readFile(String fileName) throws IOException {
		StringBuilder sb = new StringBuilder();
		try(BufferedReader br = ResourceFileReader.newBufferedReader(fileName)) {
			String line = null;
			while((line = br.readLine())!=null){
				sb.append(line).append(LF);
			}
		} catch (IOException e) {
			logger.error("failed to read " + fileName, e);
			throw e;
		}
		return sb.toString();
	}
}
