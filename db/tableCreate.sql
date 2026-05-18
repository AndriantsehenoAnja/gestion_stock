CREATE DATABASE stockdb;
\c stockdb;
-- =========================================
-- TABLE PRODUIT
-- =========================================

CREATE TABLE produit (

    id SERIAL PRIMARY KEY,

    name VARCHAR(150) NOT NULL,

    type VARCHAR(10) NOT NULL

    CHECK (
        type IN ('LIFO', 'FIFO', 'CUMP')
    )
);

-- =========================================
-- TABLE MOUVEMENT
-- =========================================

CREATE TABLE mouvement (

    id SERIAL PRIMARY KEY,

    produit_id INTEGER NOT NULL,

    quantite INTEGER NOT NULL
    CHECK (quantite > 0),

    pu DECIMAL(15,2) NOT NULL
    CHECK (pu >= 0),

    prix_total DECIMAL(15,2)
    GENERATED ALWAYS AS (quantite * pu) STORED,

    date_mouvement DATE NOT NULL,

    type VARCHAR(10) NOT NULL

    CHECK (
        type IN ('ENTREE', 'SORTIE')
    ),

    CONSTRAINT fk_mouvement_produit
        FOREIGN KEY (produit_id)
        REFERENCES produit(id)
        ON DELETE CASCADE
);

-- =========================================
-- INDEX
-- =========================================

CREATE INDEX idx_mouvement_produit
ON mouvement(produit_id);

CREATE INDEX idx_mouvement_date
ON mouvement(date_mouvement);

CREATE OR REPLACE VIEW mouvement_v AS

SELECT
    m.id,

    m.produit_id,

    p.name AS produit_name,

    p.type AS methode_stock,

    m.quantite,

    -- PU
    (
        CASE
            WHEN m.type = 'ENTREE'
            THEN m.pu::numeric(15,2)

            WHEN m.type = 'SORTIE'
            THEN (
                SELECT
                    CASE
                        WHEN SUM(
                            CASE
                                WHEN mv.type = 'ENTREE'
                                THEN mv.quantite

                                WHEN mv.type = 'SORTIE'
                                THEN -mv.quantite
                            END
                        ) = 0
                        THEN 0::numeric(15,2)

                        ELSE
                        (
                            SUM(
                                CASE
                                    WHEN mv.type = 'ENTREE'
                                    THEN mv.prix_total

                                    WHEN mv.type = 'SORTIE'
                                    THEN -mv.prix_total
                                END
                            )

                            /

                            SUM(
                                CASE
                                    WHEN mv.type = 'ENTREE'
                                    THEN mv.quantite

                                    WHEN mv.type = 'SORTIE'
                                    THEN -mv.quantite
                                END
                            )
                        )::numeric(15,2)
                    END

                FROM mouvement mv

                WHERE mv.produit_id = m.produit_id

                AND (
                    mv.date_mouvement < m.date_mouvement

                    OR (
                        mv.date_mouvement = m.date_mouvement
                        AND mv.id <= m.id
                    )
                )
            )
        END
    )::numeric(15,2) AS pu,

    m.prix_total::numeric(15,2),

    m.date_mouvement,

    m.type AS type_mouvement,

    -- Quantité totale
    (
        SELECT
            COALESCE(
                SUM(
                    CASE
                        WHEN mv.type = 'ENTREE'
                        THEN mv.quantite

                        WHEN mv.type = 'SORTIE'
                        THEN -mv.quantite
                    END
                ),
                0
            )

        FROM mouvement mv

        WHERE mv.produit_id = m.produit_id

        AND (
            mv.date_mouvement < m.date_mouvement

            OR (
                mv.date_mouvement = m.date_mouvement
                AND mv.id <= m.id
            )
        )
    ) AS quantite_total,

    -- Valeur stock
    (
        SELECT
            COALESCE(
                SUM(
                    CASE
                        WHEN mv.type = 'ENTREE'
                        THEN mv.prix_total

                        WHEN mv.type = 'SORTIE'
                        THEN -mv.prix_total
                    END
                ),
                0
            )

        FROM mouvement mv

        WHERE mv.produit_id = m.produit_id

        AND (
            mv.date_mouvement < m.date_mouvement

            OR (
                mv.date_mouvement = m.date_mouvement
                AND mv.id <= m.id
            )
        )
    ) AS valeur_stock,

    -- CUMP
    (
        SELECT
            CASE
                WHEN SUM(
                    CASE
                        WHEN mv.type = 'ENTREE'
                        THEN mv.quantite

                        WHEN mv.type = 'SORTIE'
                        THEN -mv.quantite
                    END
                ) = 0

                THEN 0

                ELSE
                (
                    SUM(
                        CASE
                            WHEN mv.type = 'ENTREE'
                            THEN mv.prix_total

                            WHEN mv.type = 'SORTIE'
                            THEN -mv.prix_total
                        END
                    )

                    /

                    SUM(
                        CASE
                            WHEN mv.type = 'ENTREE'
                            THEN mv.quantite

                            WHEN mv.type = 'SORTIE'
                            THEN -mv.quantite
                        END
                    )
                )

            END

        FROM mouvement mv

        WHERE mv.produit_id = m.produit_id

        AND (
            mv.date_mouvement < m.date_mouvement

            OR (
                mv.date_mouvement = m.date_mouvement
                AND mv.id <= m.id
            )
        )

    ) AS cump

FROM mouvement m

JOIN produit p
ON m.produit_id = p.id;