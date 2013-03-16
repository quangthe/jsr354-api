/**
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  <JSR-354  Money and Currency API > ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money;

/**
 * This interface defines a {@link Rounding}. It is hereby important to
 * distinguish between internal rounding such as implied by the maximal
 * precision/scale of an amount, contrary to rounding applied to a
 * {@link MonetaryAmount} or a calculation algorithm. Since different use cases
 * may require {@link Rounding} done at very different stages within a complex
 * financial calculation, {@link Rounding}is not directly attached with its
 * type.
 * <p>
 * Nevertheless the JSR's extensions provide a RoundingMonetaryAmount, which
 * wraps a {@link MonetaryAmount} and adds implicit rounding.
 * 
 * @author Anatole Tresch
 */
public interface Rounding {

	/**
	 * This method is called for rounding an amount.
	 * 
	 * @param amount
	 *            the amount to be rounded
	 * @return the rounded amount.
	 * @throws ArithmeticException
	 *             if rounding fails.
	 */
	public MonetaryAmount round(MonetaryAmount amount);
	

}