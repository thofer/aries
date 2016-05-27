/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIESOR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.aries.tx.control.service.xa.impl;

import java.util.Dictionary;
import java.util.Hashtable;

import javax.transaction.xa.XAException;

import org.apache.aries.tx.control.service.common.activator.AbstractActivator;
import org.apache.geronimo.transaction.manager.GeronimoTransactionManager;
import org.osgi.service.transaction.control.TransactionControl;

public class Activator extends AbstractActivator {

	private final GeronimoTransactionManager transactionManager;
	
	{
		try {
			transactionManager = new GeronimoTransactionManager();
		} catch (XAException e) {
			throw new RuntimeException("Unable to create the Transaction Manager");
		}
	}
	
	@Override
	protected TransactionControl getTransactionControl() {
		return new TransactionControlImpl(transactionManager);
	}
	
	@Override
	protected Dictionary<String, Object> getProperties() {
		Dictionary<String, Object> props = new Hashtable<>();
		props.put("osgi.local.enabled", Boolean.TRUE);
		props.put("osgi.xa.enabled", Boolean.TRUE);
		return props;
	}
}
