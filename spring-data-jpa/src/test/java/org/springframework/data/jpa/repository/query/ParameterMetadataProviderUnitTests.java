/*
 * Copyright 2017-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.jpa.repository.query;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;

import jakarta.persistence.criteria.CriteriaBuilder;

import org.junit.jupiter.api.Test;
import org.springframework.data.repository.query.Parameters;
import org.springframework.data.repository.query.parser.Part;

/**
 * Unit tests for {@link ParameterMetadataProvider}.
 *
 * @author Jens Schauder
 */
class ParameterMetadataProviderUnitTests {

	@Test // DATAJPA-863
	void errorMessageMentionesParametersWhenParametersAreExhausted() {

		CriteriaBuilder builder = mock(CriteriaBuilder.class);

		Parameters<?, ?> parameters = mock(Parameters.class, RETURNS_DEEP_STUBS);
		when(parameters.getBindableParameters().iterator()).thenReturn(Collections.emptyListIterator());

		ParameterMetadataProvider metadataProvider = new ParameterMetadataProvider(builder, parameters,
				EscapeCharacter.DEFAULT);

		assertThatExceptionOfType(RuntimeException.class) //
				.isThrownBy(() -> metadataProvider.next(mock(Part.class))) //
				.withMessageContaining("parameter");
	}

}
