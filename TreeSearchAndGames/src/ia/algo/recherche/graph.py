import matplotlib.pyplot as plt

# Données
data = {
    "XOR": {
        "Sigmoïde": [14908, 4042, 1612],
        "Hyperbolique": [696, 174, 99999],
        "Taux d'apprentissage": [0.1, 0.5, 1],
    },
    "AND": {
        "Sigmoïde": [6595, 1349, 740],
        "Hyperbolique": [1191, 171, 87059],
        "Taux d'apprentissage": [0.1, 0.5, 1],
    },
    "OR": {
        "Sigmoïde": [7246, 1489, 710],
        "Hyperbolique": [202, 35, 1042],
        "Taux d'apprentissage": [0.1, 0.5, 1],
    }
}

# Fonction pour tracer les graphiques
def plot_graph(problem, sigmoide_data, hyperbolique_data, taux):
    plt.figure(figsize=(8, 6))
    plt.plot(taux, sigmoide_data, label="Sigmoïde", marker='o')
    plt.plot(taux, hyperbolique_data, label="Hyperbolique", marker='x')
    plt.title(f"Évolution des itérations pour convergence - {problem}")
    plt.xlabel("Taux d'apprentissage")
    plt.ylabel("Nombre d'itérations")
    plt.legend()
    plt.grid(True)
    plt.show()

# Tracer les graphiques pour chaque problème
for problem in data:
    plot_graph(problem, data[problem]["Sigmoïde"], data[problem]["Hyperbolique"], data[problem]["Taux d'apprentissage"])
