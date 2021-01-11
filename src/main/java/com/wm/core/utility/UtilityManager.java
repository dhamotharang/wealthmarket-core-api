
package com.wm.core.utility;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilityManager {

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";
	private Pattern pattern;
	private Matcher matcher;

	private final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	public String ConvertStringArrayListToString(ArrayList<String> arraylist) {
		String result = String.join(",", arraylist);// convert arraylist to string
		return result;
	}

	public java.sql.Date CurrentDate() throws ParseException {
		Calendar currentdate = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMM-dd");
		String Placeholder = formatter.format(currentdate.getTime());
		java.util.Date datenow = formatter.parse(Placeholder);
		java.sql.Date CurrentDate = new Date(datenow.getTime());
		return CurrentDate;
	}

	public java.sql.Time CurrentTime() {
		java.util.Date today = new java.util.Date();
		return new java.sql.Time(today.getTime());
	}

	public String GenerateAlphaNumericCode(int LengthOfCode) {
		char[] chars = "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < LengthOfCode; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		String output = sb.toString();
		return output;
	}

	public String generateRandomNumber(int LengthOfCode) {
		String PIN = "";
		char[] chars = "1234567890".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < LengthOfCode; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		PIN = sb.toString();
		if ((PIN.length() != LengthOfCode) || PIN.startsWith("0")) {
			generateRandomNumber(LengthOfCode);
		}
		return PIN;
	}

	public java.sql.Date GetExpiryDate(int value) throws ParseException {
		Calendar currentdate = Calendar.getInstance();
		currentdate.add(Calendar.DATE, value);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMM-dd");
		String Placeholder = formatter.format(currentdate.getTime());
		java.util.Date datenow = formatter.parse(Placeholder);
		java.sql.Date CurrentDate = new Date(datenow.getTime());
		return CurrentDate;
	}

	public LocalDate getJavaDateFromString(String StringDate) {
		String format = "dd-MM-uuuu";
		LocalDate date;
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(format, Locale.ENGLISH);
		try {
			date = LocalDate.parse(StringDate, dateFormatter);
		} catch (DateTimeParseException e) {
			date = null;
		}
		return date;
	}

	public java.sql.Date getSqlDateFromString(String StringDate) {
		Date date;
		try {
			date = Date.valueOf(StringDate);
		} catch (Exception e) {
			date = null;
		}
		return date;
	}

	public java.sql.Time getSqlTimeFromString(String TimeString) {
		Time time;
		try {
			time = Time.valueOf(TimeString);
		} catch (Exception e) {
			time = null;
		}
		return time;
	}

	public String getTextAfterCharacter(String text, String character) {
		String res = text.substring(text.indexOf(character) + 1, text.length());
		return res;
	}

	public String getTextBeforeCharacter(String text, String character) {
		String res = text.substring(0, text.indexOf(character));
		return res;
	}

	public Long getTimeDifferenceInMilliseconds(java.util.Date firstDate, java.util.Date secondDate) {
		long diff = secondDate.getTime() - firstDate.getTime();
		return diff;
	}

	public int getYearDiffrenceFromLocalDate(LocalDate checkDate) {
		LocalDate now = LocalDate.now();
		Period diff = Period.between(checkDate, now);
		int years = diff.getYears();
		return years;
	}

	public boolean isValidDate(String StringDate, String format) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(format, Locale.ENGLISH);
		try {
			LocalDate.parse(StringDate, dateFormatter);
		} catch (DateTimeParseException e) {
			return false;
		}
		return true;
	}

	public boolean isValidEmail(String email) {
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(email);
		return matcher.matches();
	}

	public boolean isValidPhone(String number, String countryCode) throws NumberParseException {
		PhoneNumberUtil numberUtil = PhoneNumberUtil.getInstance();
		PhoneNumber phoneNumber = numberUtil.parse(number, countryCode);
		boolean result = numberUtil.isValidNumber(phoneNumber);
		return result;
	}

	public String PunctuatePrice(String price) {
		if (price.length() > 3) {
			price = price.substring(0, price.length() - 3) + "," + price.substring(price.length() - 3, price.length());
		}
		return price;
	}

	public String randomAlphaNumeric(int count) {
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}

	public int RandomNumber(int max, int min) {
		Random rand = new Random();
		int itID = rand.nextInt((max - min) + 1) + min;
		return itID;
	}

	public ArrayList<Integer> removeDuplicatesIntegerArrayList(ArrayList<Integer> arraylist) {
		ArrayList<Integer> result = new ArrayList<>(new LinkedHashSet<>(arraylist));
		return result;
	}

	public ArrayList<String> removeDuplicatesStringArrayList(ArrayList<String> arraylist) {
		ArrayList<String> result = new ArrayList<>(new LinkedHashSet<>(arraylist));
		return result;
	}

	public ArrayList<Integer> SortAndReverseIntegerArrayList(ArrayList<Integer> arraylist) {
		Collections.sort(arraylist);
		Collections.reverse(arraylist);
		return arraylist;
	}

	public LinkedHashMap<Integer, String> SortHashMapIntStringByValues(HashMap<Integer, String> passedMap) {
		List<Integer> mapKeys = new ArrayList<>(passedMap.keySet());
		List<String> mapValues = new ArrayList<>(passedMap.values());
		Collections.sort(mapValues);
		Collections.sort(mapKeys);

		LinkedHashMap<Integer, String> sortedMap = new LinkedHashMap<>();

		Iterator<String> valueIt = mapValues.iterator();
		while (valueIt.hasNext()) {
			String val = valueIt.next().trim();
			Iterator<Integer> keyIt = mapKeys.iterator();
			while (keyIt.hasNext()) {
				int key = keyIt.next();
				String comp1 = passedMap.get(key);
				String comp2 = val;
				if (comp1.trim().equals(comp2.trim())) {
					keyIt.remove();
					sortedMap.put(key, val);
					break;
				}
			}
		}
		return sortedMap;
	}

	public ArrayList<Integer> SortHashMapIntStringReturnArrayListInt(HashMap<Integer, String> passedMap) {
		List<Integer> mapKeys = new ArrayList<>(passedMap.keySet());
		List<String> mapValues = new ArrayList<>(passedMap.values());
		Collections.sort(mapValues);
		Collections.sort(mapKeys);

		ArrayList<Integer> sortedList = new ArrayList<>();

		Iterator<String> valueIt = mapValues.iterator();
		while (valueIt.hasNext()) {
			String val = valueIt.next().trim();
			Iterator<Integer> keyIt = mapKeys.iterator();

			while (keyIt.hasNext()) {
				int key = keyIt.next();
				String comp1 = passedMap.get(key);
				String comp2 = val;

				if (comp1.equals(comp2)) {
					keyIt.remove();
					sortedList.add(key);
					break;
				}
			}
		}
		return sortedList;
	}

	public LinkedHashMap<String, String> sortHashMapStringStringByValues(HashMap<String, String> passedMap) {
		List<String> mapKeys = new ArrayList<>(passedMap.keySet());
		List<String> mapValues = new ArrayList<>(passedMap.values());
		Collections.sort(mapValues);
		Collections.sort(mapKeys);

		LinkedHashMap<String, String> sortedMap = new LinkedHashMap<>();

		Iterator<String> valueIt = mapValues.iterator();
		while (valueIt.hasNext()) {
			String val = valueIt.next().trim();
			Iterator<String> keyIt = mapKeys.iterator();

			while (keyIt.hasNext()) {
				String key = keyIt.next().trim();
				String comp1 = passedMap.get(key);
				String comp2 = val;

				if (comp1.equals(comp2)) {
					keyIt.remove();
					sortedMap.put(key, val);
					break;
				}
			}
		}
		return sortedMap;
	}
}
