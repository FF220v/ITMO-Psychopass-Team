import json
import os
import base64
from jinja2 import Template

if __name__ == "__main__":
    templates_counter = 0
    os.chdir("./work_dir")
    params = dict()
    for filename in filter(lambda fn: fn.endswith(".json"), os.listdir(".")):
        print(f"Read parameters from {filename}")
        with open(filename) as f:
            params.update(json.load(f))

    # Namespaces go first, other resources can be deployed in any order
    files_structure = sorted(os.walk("kubernetes_templates"), key=lambda x: not x[0].endswith("namespaces"))

    with open("kube_config.yaml", "w+") as results_f:
        for path, _, filenames in files_structure:
            for filename in filter(lambda fn: fn.endswith(".yaml") or fn.endswith(".yml"), filenames):
                with open(f"{path}/{filename}") as f:
                    print(f"Rendering template {filename}")
                    templates_counter += 1
                    template = Template(f.read())
                    rendered = template.render(params)
                    results_f.write(rendered)
                    results_f.write("\n---\n")

    print(f"{templates_counter} templates rendered successfully")
