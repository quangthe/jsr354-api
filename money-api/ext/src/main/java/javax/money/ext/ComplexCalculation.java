/**
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  <JSR-354  Money and Currency API > ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money.ext;

/**
 * This interface encapsulates a complex calculation that uses
 * {@link CompoundValue} instances, both for input and result representation.
 * 
 * @author Anatole Tresch
 */
public interface ComplexCalculation {

	/**
	 * Returns an literal non localized name, that identifies this type of
	 * calculation.
	 * 
	 * @return the identifier, not null.
	 */
	public String getId();

	/**
	 * Returns a literal non localized name, that identifies this type of
	 * calculation.
	 * 
	 * @return the identifier, not null.
	 */
	public CompoundValueTemplate getInputType();

	/**
	 * Returns a literal non localized name, that identifies this type of
	 * calculation.
	 * 
	 * @return the identifier, not null.
	 */
	public CompoundValueTemplate getOutputType();

	/**
	 * Returns a complex {@link CompoundValue} as a result one another
	 * {@link CompoundValue}.
	 * 
	 * @param value
	 *            the {@link CompoundValue} to use, not null
	 * @return the calculation result as a {@link CompoundValue}, never null
	 * @throws CalculationException
	 *             if the calculation fails
	 */
	public CompoundValue calculate(CompoundValue value);

}
