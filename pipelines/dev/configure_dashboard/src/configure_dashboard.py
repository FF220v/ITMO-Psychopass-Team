import yaml

if __name__ == "__main__":
    with open("./work_dir/kubernetes_dashboard.yaml", "r") as f:
        config = yaml.load(f.read(), Loader=yaml.FullLoader)
    args = set(config["spec"]["template"]["spec"]["containers"][0]["args"])
    args.update({"--enable-skip-login"})
    config["spec"]["template"]["spec"]["containers"][0]["args"] = list(args)
    with open("./work_dir/kubernetes_dashboard.yaml", "w+") as f:
        f.write(yaml.dump(config))