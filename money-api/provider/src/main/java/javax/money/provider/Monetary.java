/**
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money.provider;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.money.convert.ConversionProvider;
import javax.money.format.ItemFormatterFactory;
import javax.money.format.ItemParserFactory;

/**
 * This is the main accessor component for Java Money. Is is responsible for
 * loading the API top level providers using the {@link ServiceLoader}:
 * <ul>
 * <li>{@code javax.money.convert.ConversionProvider}</li>
 * <li>{@code javax.money.format.ItemFormatterFactory}</li>
 * <li>{@code javax.money.format.ItemParserFactory}</li>
 * <li>{@code javax.money.provider.CurrencyUnitProvider}</li>
 * <li>{@code javax.money.provider.HistoricCurrencyUnitProvider}</li>
 * <li>{@code javax.money.provider.RoundingProvider}</li>
 * </ul>
 * 
 * Additionally it is also responsible for loading the
 * {@link AnnotationServiceSpi} support SPI component, which provides annotation
 * lookup/search functionality.
 * <ul>
 * <li>{@code javax.money.provider.spi.AnnotationServiceSpi}</li>
 * </ul>
 * 
 * The top level API implementations then are required to load the annotated
 * interfaces automatically.<br/>
 * The SPIs supported are determined by the different modules. JSR 354 includes
 * the following spi packages:
 * <ul>
 * <li>{@code javax.money.convert.spi}</li>
 * <li>{@code javax.money.format.spi}</li>
 * <li>{@code javax.money.provider.spi}</li>
 * <li>{@code javax.money.ext.spi}</li>
 * </ul>
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 * @version 0.9
 * 
 */
public final class Monetary {
	private static final Logger LOGGER = Logger.getLogger(Monetary.class
			.getName());

	private static final ComponentLoader LOADER = initLoader();

	private static final Monetary INSTANCE = new Monetary();

	private ItemFormatterFactory itemFormatterFactory;

	private ItemParserFactory itemParserFactory;

	private RoundingProvider roundingProvider;

	private ConversionProvider conversionProvider;

	private CurrencyUnitProvider currencyUnitProvider;
	
	private HistoricCurrencyUnitProvider historicCurrencyUnitProvider;

	/**
	 * Singleton constructor.
	 */
	private Monetary() {
	}

	private static ComponentLoader initLoader() {
		ComponentLoader loader = null;
		try {
			// try loading directly from ServiceLoader
			Iterator<ComponentLoader> loaders = ServiceLoader.load(
					ComponentLoader.class).iterator();
			if (loaders.hasNext()) {
				loader = loaders.next();
				loader.init();
				return loader;
			}
		} catch (Exception e) {
			LOGGER.log(Level.INFO,
					"No ComponentLoader found, using ServiceLoader default.", e);
		}
		return new DefaultServiceLoader();
	}


	/**
	 * Access the {@link CurrencyUnitProvider} component.
	 * 
	 * @return the {@link CurrencyUnitProvider} component, never {@code null}.
	 */
	@SuppressWarnings("unchecked")
	public static CurrencyUnitProvider getCurrencyUnitProvider() {
		if (INSTANCE.currencyUnitProvider == null) {
			INSTANCE.currencyUnitProvider = LOADER
					.getComponent(CurrencyUnitProvider.class);
		}
		if (INSTANCE.currencyUnitProvider == null) {
			throw new UnsupportedOperationException(
					"No CurrencyUnitProvider loaded");
		}
		return INSTANCE.currencyUnitProvider;
	}
	
	/**
	 * Access the {@link CurrencyUnitProvider} component.
	 * 
	 * @return the {@link CurrencyUnitProvider} component, never {@code null}.
	 */
	@SuppressWarnings("unchecked")
	public static HistoricCurrencyUnitProvider getHistoricCurrencyUnitProvider() {
		if (INSTANCE.historicCurrencyUnitProvider == null) {
			INSTANCE.historicCurrencyUnitProvider = LOADER
					.getComponent(HistoricCurrencyUnitProvider.class);
		}
		if (INSTANCE.historicCurrencyUnitProvider == null) {
			throw new UnsupportedOperationException(
					"No HistoricCurrencyUnitProvider loaded");
		}
		return INSTANCE.historicCurrencyUnitProvider;
	}
	

	/**
	 * Access the {@link ConversionProvider} component.
	 * 
	 * @return the {@link ConversionProvider} component, never {@code null}.
	 * @throws IllegalArgumentException
	 *             if no such provider is registered.
	 */
	@SuppressWarnings("unchecked")
	public static ConversionProvider getConversionProvider() {
		if (INSTANCE.conversionProvider == null) {
			INSTANCE.conversionProvider = LOADER
					.getComponent(ConversionProvider.class);
			if (INSTANCE.conversionProvider == null) {
				throw new UnsupportedOperationException(
						"No ConversionProvider loaded");
			}
		}
		return INSTANCE.conversionProvider;
	}

	/**
	 * Access the {@link ItemFormatterFactory} component.
	 * 
	 * @return the {@link ItemFormatterFactory} component, never {@code null}.
	 */
	@SuppressWarnings("unchecked")
	public static ItemFormatterFactory getItemFormatterFactory() {
		if (INSTANCE.itemFormatterFactory == null) {
			INSTANCE.itemFormatterFactory = LOADER
					.getComponent(ItemFormatterFactory.class);
			if (INSTANCE.itemFormatterFactory == null) {
				throw new UnsupportedOperationException(
						"No ItemFormatterFactory loaded");
			}
		}
		return INSTANCE.itemFormatterFactory;
	}

	/**
	 * Access the {@link ItemParserFactory} component.
	 * 
	 * @return the {@link ItemParserFactory} component, never {@code null}.
	 */
	@SuppressWarnings("unchecked")
	public static ItemParserFactory getItemParserFactory() {
		if (INSTANCE.itemParserFactory == null) {
			INSTANCE.itemParserFactory = LOADER
					.getComponent(ItemParserFactory.class);
			if (INSTANCE.itemParserFactory == null) {
				throw new UnsupportedOperationException(
						"No ItemParserFactory loaded");
			}
		}
		return INSTANCE.itemParserFactory;
	}

	/**
	 * Access the {@link RoundingProvider} component.
	 * 
	 * @return the {@link RoundingProvider} component, never {@code null}.
	 */
	@SuppressWarnings("unchecked")
	public static RoundingProvider getRoundingProvider() {
		if (INSTANCE.roundingProvider == null) {
			INSTANCE.roundingProvider = LOADER
					.getComponent(RoundingProvider.class);
			if (INSTANCE.roundingProvider == null) {
				throw new UnsupportedOperationException(
						"No RoudingProvider loaded");
			}
		}
		return INSTANCE.roundingProvider;
	}

	/**
	 * Access the {@link ComponentLoader} that is used by this accessor
	 * instance.
	 * 
	 * @return the loder in use, never null.
	 */
	public static ComponentLoader getLoader() {
		return LOADER;
	}

	/**
	 * This is the loader that is used to load the different providers and spi
	 * to be used by {@link Monetary} and its services. The
	 * {@link ComponentLoader} can also be accessed from the {@link Monetary}
	 * singleton, so it can also be used by the monetary service
	 * implementations.
	 * 
	 * @author Anatole Tresch
	 */
	public static interface ComponentLoader {

		/**
		 * Method to initialize the component loader instance.
		 */
		public void init();

		/**
		 * Access a singleton instance.
		 * 
		 * @param type
		 *            The target type.
		 * @param annotations
		 *            The annotations that must be present on the type.
		 * @return the instance found, or null.
		 * @throws IllegalStateException
		 *             , when the instances are ambiguous.
		 */
		@SuppressWarnings("unchecked")
		public <T> T getComponent(Class<T> type,
				Class<? extends Annotation>... annotations);

		/**
		 * Access a list of instances.
		 * 
		 * @param type
		 *            The target type.
		 * @param annotations
		 *            The annotations that must be present on the types.
		 * @return the instances matching, never null.
		 */
		@SuppressWarnings("unchecked")
		public <T> List<T> getComponents(Class<T> type,
				Class<? extends Annotation>... annotations);

	}

	public final static class DefaultServiceLoader implements ComponentLoader {

		@Override
		public void init() {
			// Nothing todo here
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T> T getComponent(Class<T> type,
				Class<? extends Annotation>... annotations) {
			List<T> instancesFound = getComponents(type, annotations);
			if (instancesFound.isEmpty()) {
				return null;
			} else if (instancesFound.size() == 1) {
				return instancesFound.get(0);
			} else {
				return resolveAmbigousComponents(instancesFound);
			}
		}

		protected <T> T resolveAmbigousComponents(List<T> instancesFound) {
			// or throw exception!
			return instancesFound.get(0);

		}

		protected boolean annotationsMatch(Object comp,
				Class<? extends Annotation>[] annotations) {
			if (annotations == null) {
				return true;
			}
			for (Class<? extends Annotation> annotType : annotations) {
				if (comp.getClass().getAnnotation(annotType) == null) {
					return false;
				}
			}
			return true;
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T> List<T> getComponents(Class<T> type,
				Class<? extends Annotation>... annotations) {
			List<T> instancesFound = new ArrayList<T>();
			ServiceLoader<T> components = ServiceLoader.load(type);
			for (T comp : components) {
				if (annotationsMatch(comp, annotations)) {
					instancesFound.add((T) comp);
				}
			}
			sortComponents(instancesFound);
			return instancesFound;
		}

		protected void sortComponents(List<?> list) {
		}

	}

}
