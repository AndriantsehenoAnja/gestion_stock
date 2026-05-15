table produit:
    id
    name 
    type :{"lifo , fifo , cump"}
table mouvement:
    produit_id : integer
    quantite : integer
    PU : decimal
    prix_total : decimal (quantite * PU)
    date : date
    type : {"entree", "sortie"}

table view mouvement_v:
    select *,(
    select sum(quantite) as quantite_total,
    sum(prix_total) as valeur_stock,
    (valeur_stock/quantite_total) as cump from mouvement group by produit_id where date <= m.date;
    )
    from mouvement m ;
    join produit p  on m.produit_id = p.id;
