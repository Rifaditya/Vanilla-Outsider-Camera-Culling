# 🪧 Occlusion de texte des panneaux (Minecraft 26.1.2)

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

Dans Minecraft 26.1.2, les panneaux disposent d'un rendu de texte sur les deux faces (`getFrontText()` et `getBackText()`). Le moteur de rendu typographique du jeu dessine les glyphes, les couleurs et les contours luisants sur les deux faces simultanément.

**Camera Culling** supprime les passes de rendu de texte inutiles grâce au calcul de produits scalaires des vecteurs normaux ($\\vec{N} \\cdot \\vec{V}$) et à une détection instantanée des faces vierges.

---

## 📋 Fiche d'information de l'occlusion des panneaux

| Propriété | Valeur |
| :--- | :--- |
| **Pipeline cible** | `BlockEntityRenderDispatcher.tryExtractRenderState` |
| **Angle de coupure avant/arrière** | Seuil de produit scalaire à $\pm 0.05$ |
| **Passage rapide face vide** | Ignore automatiquement les faces ne contenant aucun caractère |
| **Blocs pris en charge** | Panneaux sur pied, muraux, suspendus au plafond et suspendus aux murs |

---

## 📐 Produit scalaire trigonométrique du vecteur normal

Pour déterminer si la face avant ou arrière d'un panneau est orientée vers la caméra, Camera Culling calcule le produit scalaire entre le vecteur normal de la face $\vec{N} = (N_x, N_z)$ et le vecteur reliant le centre du panneau à la caméra $\vec{V} = (V_x, V_z)$ :

$$V_x = X_{\text{cam}} - (X_{\text{panneau}} + 0.5), \quad V_z = Z_{\text{cam}} - (Z_{\text{panneau}} + 0.5)$$

### 1. Panneaux muraux (`WallSignBlock.FACING`) & suspendus muraux (`WallHangingSignBlock.FACING`)
Le vecteur normal est directement extrait des décalages directionnels de l'orientation du bloc (`Direction`) :
$$N_x = \text{facing.getStepX()}, \quad N_z = \text{facing.getStepZ()}$$

### 2. Panneaux sur pied (`StandingSignBlock.ROTATION`) & suspendus au plafond (`CeilingHangingSignBlock.ROTATION`)
La rotation est représentée sous forme d'entier de $0 \dots 15$. L'angle $\theta$ en radians est calculé :
$$\theta = \left(\frac{\text{rotation} \times 360^\circ}{16}\right) \times \frac{\pi}{180}$$
$$N_x = -\sin\theta, \quad N_z = \cos\theta$$

### 3. Détermination de la visibilité
Le produit scalaire $D$ évalue l'angle d'observation :
$$D = N_x V_x + N_z V_z$$

* **Évaluation de la face avant** : Masquée lorsque $D < -0.05$ (la caméra est située derrière la face).
* **Évaluation de la face arrière** : Masquée lorsque $D > 0.05$ (la caméra est située devant la face).

---

## ⚡ Échec rapide pour face vide (Fast-Pass)

Si un joueur n'a écrit de texte que sur une seule face d'un panneau (ou s'il a posé un panneau vierge en tant qu'élément de décoration), Camera Culling inspecte les lignes de texte :
```java
public static boolean isTextEmpty(SignText text) {
    if (text == null) return true;
    for (int i = 0; i < 4; i++) {
        Component msg = text.getMessage(i, false);
        if (msg != null && !msg.getString().trim().isEmpty()) {
            return false;
        }
    }
    return true;
}
```
Les faces vierges sont immédiatement ignorées sans exécuter de calculs trigonométriques ou de produits scalaires.

---

## 🔗 Pages connexes

- [[Occlusion des entités de bloc|fr_fr-26.1.2-Block-Entity-Culling]]
- [[Commandes et configuration|fr_fr-26.1.2-Commands-and-Configuration]]
- [[Architecture et Mixins|fr_fr-26.1.2-Architecture-and-Mixins]]
- [[Retour à la vue d'ensemble MC 26.1.2|fr_fr-26.1.2-Home]]
