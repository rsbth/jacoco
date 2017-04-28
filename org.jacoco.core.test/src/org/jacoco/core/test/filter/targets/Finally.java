/*******************************************************************************
 * Copyright (c) 2009, 2017 Mountainminds GmbH & Co. KG and Contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Evgeny Mandrikov - initial API and implementation
 *
 *******************************************************************************/
package org.jacoco.core.test.filter.targets;

import static org.jacoco.core.test.validation.targets.Stubs.nop;

/**
 * This test target is a finally block.
 */
public class Finally {

	private static void ex(boolean t) {
		if (t) {
			throw new RuntimeException();
		}
	}

	private static void branches(boolean t) {
		int i = 0;
		try {
			ex(t);
			i++;
		} catch (Exception e) {
			nop();
		} finally {
			nop(i == 0); // $line-conditionalJump$

			switch (i) { // $line-tableswitch$
			case 0:
			case 2:
				break;
			case 1:
			default:
				break;
			}

			switch (i) { // $line-lookupswitch$
			case 0:
				break;
			default:
				break;
			}
		}
	}

	private static Object returnInBody() {
		try {
			return null;
		} finally {
			nop(); // $line-returnInBody$
		}
	}

	/**
	 * Note that in this case javac generates bytecode that uses different slots
	 * in different duplicates of same finally block for variable that stores
	 * exception.
	 */
	private static void nested() {
		try {
			nop();
		} finally {
			try {
				nop();
			} finally {
				nop(); // $line-nested$
			}
		}
	}

	public static void main(String[] args) {
		branches(false);
		try {
			branches(true);
		} catch (Exception ignore) {
		}

		returnInBody();

		nested();
	}

}
