import json
import os
from jinja2 import Template

if __name__ == "__main__":
    os.chdir("./work_dir")
    params = dict()
    for filename in filter(lambda fn: fn.endswith(".json"), os.listdir(".")):
        with open(filename) as f:
            params.update(json.load(f))

    with open("kube_config.yaml", "w+") as results_f:
        for path, _, filenames in os.walk("kubernetes_templates"):
            for filename in filter(lambda fn: fn.endswith(".yaml") or fn.endswith(".yml"), filenames):
                with open(f"{path}/{filename}") as f:
                    template = Template(f.read())
                    rendered = template.render(params)
                    results_f.write(rendered)
                    results_f.write("\n---\n")