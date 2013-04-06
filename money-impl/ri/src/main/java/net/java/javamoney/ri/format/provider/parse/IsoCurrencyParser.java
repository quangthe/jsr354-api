/*
 *  Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 * Contributors:
 *    Anatole Tresch - initial implementation
 */
package net.java.javamoney.ri.format.provider.parse;

import java.util.Arrays;
import java.util.Locale;

import javax.money.CurrencyUnit;
import javax.money.MoneyCurrency;
import javax.money.format.ItemParseException;
import javax.money.format.ItemParser;
import javax.money.format.LocalizationStyle;


public class IsoCurrencyParser implements ItemParser<CurrencyUnit> {

	public enum ParsedField {
		ID, CODE
	}

	private ParsedField[] parsedFields = new ParsedField[] { ParsedField.CODE };
	private LocalizationStyle style;

	@Override
	public Class<CurrencyUnit> getTargetClass() {
		return CurrencyUnit.class;
	}

	public IsoCurrencyParser(LocalizationStyle style)
			throws ItemParseException {
		String value = (String) style
				.getAttribute("parsedFields", String.class);
		if (value != null) {
			try {
				String[] fields = value.split(",");
				parsedFields = new ParsedField[fields.length];
				for (int i = 0; i < fields.length; i++) {
					parsedFields[i] = ParsedField.valueOf(fields[i]
							.toUpperCase(Locale.ENGLISH));
				}
			} catch (Exception e) {
				throw new ItemParseException(
						"parsedFields must be a comma separated list of "
								+ Arrays.toString(ParsedField.values()));
			}
		}
		this.style = style;
	}

	@Override
	public LocalizationStyle getStyle() {
		return style;
	}

	@Override
	public CurrencyUnit parse(CharSequence text) throws ItemParseException {
		// try to check for non localizaed formats
		String namespace = this.style.getAttribute("namespace", String.class);
		String currencyCode = null;
		String textString = text.toString();
		for (ParsedField f : parsedFields) {
			switch (f) {
			case ID:
				int index = textString.indexOf(':');
				if (index <= 0) {
					throw new ItemParseException(
							"Currency ID format required namespace:code, bu was: " + text);
				}
				namespace = textString.substring(0, index);
				currencyCode = textString.substring(index + 1);
				return MoneyCurrency.of(namespace, currencyCode);
			case CODE:
				if (namespace == null) {
					throw new ItemParseException(
							"Currency CODE format requires namespace attribute, but was:: " + text);
				}
				return MoneyCurrency.of(namespace, text.toString());
			}
		}
		throw new ItemParseException("Currency not parseable: " + text);
	}
}
