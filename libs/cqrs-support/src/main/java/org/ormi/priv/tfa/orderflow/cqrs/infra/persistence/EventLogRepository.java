package org.ormi.priv.tfa.orderflow.cqrs.infra.persistence;

import org.ormi.priv.tfa.orderflow.cqrs.EventEnvelope;
import org.ormi.priv.tfa.orderflow.cqrs.infra.jpa.EventLogEntity;

/**
 * Contrat pour l'accès au journal des événements.
 * Permet d'enregistrer les événements domaines de manière persistante.
 */

public interface EventLogRepository {
    EventLogEntity append(EventEnvelope<?> eventLog);
}
